package com.xcaret.loyaltyreps.view.training.vm

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcaret.loyaltyreps.data.api.VideoTraining
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.usecase.ApiUseCase
import com.xcaret.loyaltyreps.data.usecase.DownloaderUseCase
import com.xcaret.loyaltyreps.data.usecase.Result
import com.xcaret.loyaltyreps.data.utils.sdk29AndUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink


data class VideoDownload(
    var isLoading: Boolean = false,
    var state: String = "done"
)

class TrainingViewModel : ViewModel() {
    var apiUseCase: ApiUseCase = ApiUseCase()
    var downloaderCase: DownloaderUseCase = DownloaderUseCase()
    var currentUser = MutableLiveData<XUser?>()
    var listParksTraining = MutableLiveData<List<XPark>>()
    var errorParkTraining = MutableLiveData<String>("")
    var listVideosTraining = MutableLiveData<List<VideoTraining>>()
    var errorVideoTraining = MutableLiveData<String>("")
    var itemParkSelected = MutableLiveData<XPark>()

    var videoDownloaded = MutableLiveData(VideoDownload())



    init {
        fetchTrainingData()
        fetchVideoTrainingData()
    }
    fun fetchTrainingData(){
        apiUseCase.fetchParks(){ list, error ->
            list?.let{
                listParksTraining.postValue(it)
            }?: run{
                errorParkTraining.postValue(error?:"Error getting the list")
            }
        }
    }
    fun fetchVideoTrainingData(){
        apiUseCase.fetchTrainingVideos(){ list, error ->
            list?.let{
                val videoList = mutableListOf<VideoTraining>()
                if(it.isNotEmpty()){
                    for (videosT in it){

                        val nVideo = videosT.copy(
                            visibility = true
                        )

                        videoList.add(
                            nVideo
                        )
                    }
                }
                Log.i("viewModel", "observers: videos $videoList")
                listVideosTraining.postValue(videoList)
            }?: run{
                errorVideoTraining.postValue(error?:"Error getting the list")
            }
        }
    }
    fun saveVideo(context: Context, activity: Activity, xVideoUrl: String, name: String){
        val result = downloaderCase.downloaderDefault(context, activity,  xVideoUrl, name,{
            Toast.makeText(context, "Video  ${name} Downloaded", Toast.LENGTH_LONG).show()
        }){

        }

    }

//    fun getUriFromUrl(context: Context, displayName: String, imgUrl: String, final: (uri: Uri?)->Unit){
//        imageDownloaded.postValue( VideoDownload(isLoading = true,state = "loading"))
//        viewModelScope.launch(Dispatchers.IO) {
//            val imageCollection = sdk29AndUp {
//                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
//            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//
//            val contentValues = ContentValues().apply {
//                put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
//                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//                put(MediaStore.Images.Media.WIDTH, resource.width)
//                put(MediaStore.Images.Media.HEIGHT, resource.height)
//            }
//
//            try {
//                getApp().mContext.contentResolver.insert(imageCollection, contentValues)?.also { uri ->
//                    getApp().mContext.contentResolver.openOutputStream(uri).use { outputStream ->
//                        outputStream?.let{
//                            if(!resource.compress(Bitmap.CompressFormat.JPEG, 95, it)) {
//                                throw IOException("Couldn't save bitmap")
//                            }
//                        }?: throw IOException("outputStream is null")
//                    }
//                    imageDownloaded.postValue( VideoDownload(isLoading = false,state = "done"))
//
//                    final(uri)
//                } ?: throw IOException("Couldn't create MediaStore entry")
//
//            } catch(e: IOException) {
//                e.printStackTrace()
//                e.message?.let{
//
//                    imageDownloaded.postValue( VideoDownload(isLoading = false,state = "done"))
//                }
//                final(null)
//            }
//
//        }
//    }

//    fun saveVideo(context: Context, activity: Activity, xVideoUrl: String, name: String):Long{
//        var cont: Boolean = true;
//
//        sdk29AndUp{}?:run{
//            val permissionCheck2: Int = ContextCompat.checkSelfPermission(
//                context!!,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            )
//            when {
//                permissionCheck2 != PackageManager.PERMISSION_GRANTED -> {
//                    cont = false;
//                    ActivityCompat.requestPermissions(activity, arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
//                }
//            }
//        }
//        kotlin.run {
//            if(cont) {
//                val videoCollection = sdk29AndUp {
//                    Environment.DIRECTORY_DOWNLOADS
////                    MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
//                } ?: MediaStore.Video.Media.EXTERNAL_CONTENT_URI.path
//                Log.i("TrainingFragment", "observers: $videoCollection")
//
//                val request = DownloadManager.Request(Uri.parse(xVideoUrl))
//                    .setTitle(name)
//                    .setDescription(name + "Downloading")
//                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                    .setAllowedOverMetered(true)
//                    .setMimeType("video/mp4")
//                    //.setDestinationInExternalPublicDir(context?.getExternalFilesDir(Environment.DIRECTORY_DCIM).toString() ,arguments?.getString("xpark_name").toString() + ".mp4")
//                    .setDestinationInExternalPublicDir(
//                        videoCollection,
//                        name + ".mp4"
//                    )
//                val dm: DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//                Toast.makeText(context, "Downloading ${name}", Toast.LENGTH_LONG).show()
//                return dm.enqueue(request)
//            }else{
//                return 0;
//            }
//        }
//    }
//    fun downLoadVideoMedia(fileUrl: String, name: String, context: Context){
//        viewModelScope.launch {
//            try {
//                val down = downloadQ(fileUrl, name, context)
//                Log.i("downloadQ", "downloadQ: $down")
//            }catch(e: Exception){
//                videoDownloaded.postValue(VideoDownload(false, "done"))
//            }
//
//        }
//    }

//    private suspend fun downloadQ(fileUrl: String, name: String, context: Context): Uri =
//        withContext(Dispatchers.IO) {
//            videoDownloaded.postValue(VideoDownload(true, "downloading"))
//            val url = fileUrl
//            val response = ok.newCall(Request.Builder().url(fileUrl).build()).execute()
//
//            val videoCollection = sdk29AndUp {
//                MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
//            } ?: run { MediaStore.Video.Media.EXTERNAL_CONTENT_URI }
//            Log.i("downloadQ", "downloadQ: $response")
//            if (response.isSuccessful) {
//                Log.i("downloadQ", "downloadQ: $response")
//                val values = ContentValues().apply {
//                    put(MediaStore.Video.Media.DISPLAY_NAME, name)
//                    put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/Loyalty")
//                    put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
//                    put(MediaStore.Video.Media.IS_PENDING, 1)
//                }
//
//                val resolver = context.contentResolver
//                val uri = resolver.insert(videoCollection, values)
//
//                uri?.let {
//                    resolver.openOutputStream(uri)?.use { outputStream ->
//                        val sink = outputStream.sink().buffer()
//
//                        response.body()?.source()?.let { sink.writeAll(it) }
//                        sink.close()
//                    }
//                    values.clear()
//                    values.put(MediaStore.Video.Media.IS_PENDING, 0)
//                    resolver.update(uri, values, null, null)
//                    videoDownloaded.postValue(VideoDownload(false, "done"))
////                    Toast.makeText(context, "Video  ${name} Downloaded", Toast.LENGTH_LONG).show()
//                } ?: throw RuntimeException("MediaStore failed for some reason")
//
//                uri
//            } else {
//                throw RuntimeException("OkHttp failed for some reason")
//            }
//        }

}