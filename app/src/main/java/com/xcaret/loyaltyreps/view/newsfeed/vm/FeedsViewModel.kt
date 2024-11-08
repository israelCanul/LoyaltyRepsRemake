package com.xcaret.loyaltyreps.view.newsfeed.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xcaret.loyaltyreps.data.api.ResponseNewsFeed
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.usecase.ApiUseCase

class FeedsViewModel(): ViewModel() {
    //getNewsFeed
    var apiUseCase: ApiUseCase = ApiUseCase()
    var newsFeedresponse = MutableLiveData<List<ResponseNewsFeed>?>(null)

    init {
        fetchTrainingData()
    }

    fun fetchTrainingData(){
        apiUseCase.getNewsFeed(){ list, error ->
            list?.let{
                Log.i("FeedsViewModel", "fetchTrainingData: $it")
                newsFeedresponse.postValue(it)
            }?: run{
                newsFeedresponse.postValue(listOf())
            }
        }
    }
}