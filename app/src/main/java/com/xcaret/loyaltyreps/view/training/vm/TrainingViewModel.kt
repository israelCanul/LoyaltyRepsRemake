package com.xcaret.loyaltyreps.view.training.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xcaret.loyaltyreps.data.api.VideoTraining
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.usecase.ApiUseCase

class TrainingViewModel : ViewModel() {
    var apiUseCase: ApiUseCase = ApiUseCase()
    var currentUser = MutableLiveData<XUser?>()
    var listParksTraining = MutableLiveData<List<XPark>>()
    var errorParkTraining = MutableLiveData<String>("")
    var listVideosTraining = MutableLiveData<List<VideoTraining>>()
    var errorVideoTraining = MutableLiveData<String>("")
    var itemParkSelected = MutableLiveData<XPark>()
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

}