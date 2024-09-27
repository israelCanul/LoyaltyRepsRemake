package com.xcaret.loyaltyreps.data.usecase

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.xcaret.loyaltyreps.data.utils.sdk29AndUp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink

sealed class Result {
    class Success(val uri: Uri?) : Result()
    data object Failure: Result()
}

class DownloaderUseCase {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val ok = OkHttpClient()

    fun downloaderDefault(context: Context, activity: Activity, xVideoUrl: String, name: String,init:()->Unit,  finally: ()->Unit){
        coroutineScope.launch {
            val uri  = saveVideo(context, activity, xVideoUrl, name, init) {
                finally()
            }
        }
    }
    fun downloaderQ(fileUrl: String, name: String, context: Context, finally: ()->Unit){
        coroutineScope.launch {
            var uri  = downloadQ(fileUrl, name, context){
                coroutineScope.launch(Dispatchers.Main){
                    finally()
                }
            }
        }
    }
    private suspend fun downloadQ(fileUrl: String, name: String, context: Context, finaly: ()->Unit): Result{
        return withContext(Dispatchers.IO) {
            val response = ok.newCall(Request.Builder().url(fileUrl).build()).execute()

            val videoCollection = sdk29AndUp {
                MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } ?: run { MediaStore.Video.Media.EXTERNAL_CONTENT_URI }

            try {
                if (response.isSuccessful) {
                    val values = ContentValues().apply {
                        put(MediaStore.Video.Media.DISPLAY_NAME, name)
                        put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/Loyalty")
                        put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                        put(MediaStore.Video.Media.IS_PENDING, 1)
                    }
                    val resolver = context.contentResolver
                    val uri = resolver.insert(videoCollection, values)

                    uri?.let {
                        resolver.openOutputStream(uri)?.use { outputStream ->
                            val sink = outputStream.sink().buffer()

                            response.body()?.source()?.let { sink.writeAll(it) }
                            sink.close()
                        }
                        values.clear()
                        values.put(MediaStore.Video.Media.IS_PENDING, 0)
                        resolver.update(uri, values, null, null)

                        return@withContext Result.Success(uri)

                    } ?: throw RuntimeException("MediaStore failed for some reason")
                } else {
                    throw RuntimeException("OkHttp failed for some reason")
                }
            }catch (e : RuntimeException){
                return@withContext Result.Failure
            }finally {
                finaly()
            }
        }
    }

    private suspend fun saveVideo(context: Context, activity: Activity, xVideoUrl: String, name: String,init: ()-> Unit, finally: ()->Unit ):Result{
        return withContext(Dispatchers.IO) {
            try {
                var cont: Boolean = true
                sdk29AndUp{}?:run{
                    val permissionCheck2: Int = ContextCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    when {
                        permissionCheck2 != PackageManager.PERMISSION_GRANTED -> {
                            cont = false;
                            ActivityCompat.requestPermissions(activity, arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
                        }
                    }
                }

                if(cont) {
                    val videoCollection = sdk29AndUp {
                        Environment.DIRECTORY_DOWNLOADS
//                    MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                    } ?: MediaStore.Video.Media.EXTERNAL_CONTENT_URI.path
                    Log.i("TrainingFragment", "observers: $videoCollection")
                    withContext(Dispatchers.Main){
                        init()
                    }
                    val request = DownloadManager.Request(Uri.parse(xVideoUrl))
                        .setTitle(name)
                        .setDescription(name + "Downloading")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setAllowedOverMetered(true)
                        .setMimeType("video/mp4")
                        //.setDestinationInExternalPublicDir(context?.getExternalFilesDir(Environment.DIRECTORY_DCIM).toString() ,arguments?.getString("xpark_name").toString() + ".mp4")
                        .setDestinationInExternalPublicDir(
                            videoCollection,
                            name + ".mp4"
                        )
                    val dm: DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                    dm.enqueue(request)
                    return@withContext Result.Success(null)
                }else{
                    throw RuntimeException("Permissions error")
                }
            }catch (e : RuntimeException){
                Log.i("TrainingFragment", "observers: $e ")
                return@withContext Result.Failure
            }finally {
                withContext(Dispatchers.Main){
                    finally()
                }
            }
        }
    }
}