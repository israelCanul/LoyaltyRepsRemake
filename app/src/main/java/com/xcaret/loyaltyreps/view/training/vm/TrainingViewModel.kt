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
import com.xcaret.loyaltyreps.data.api.TrainingSection
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
    var parkTrainingDetail = MutableLiveData<TrainingSection?>(null)


    init {
//        fetchTrainingData()
//        fetchVideoTrainingData()
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

    fun getParkTraining(parkId: String){
        parkTrainingDetail.postValue(null)
//        fetchTrainingDetail
        viewModelScope.launch(Dispatchers.IO) {
            apiUseCase.fetchTrainingDetail(parkId){ training, error ->
                viewModelScope.launch(Dispatchers.Main){
                    training?.let{
                        parkTrainingDetail.postValue(it)
                    }?: run{
                        parkTrainingDetail.postValue(TrainingSection())
                        errorParkTraining.postValue(error?:"Error getting the list")
                    }
                }

            }
        }
    }

}