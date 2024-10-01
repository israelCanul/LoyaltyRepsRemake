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
import com.xcaret.loyaltyreps.data.api.VideoQuizTraining
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
import kotlin.math.log


class VideoQuizzViewModel : ViewModel() {
    var apiUseCase: ApiUseCase = ApiUseCase()
    var currentUser = MutableLiveData<XUser?>()
    var videoQuizTraining = MutableLiveData<VideoQuizTraining?>()



    init {
        fetchVideoTrainingData()
    }

    fun fetchVideoTrainingData(){
        viewModelScope.launch {
            apiUseCase.fetchVideoQuizTraining("40") { item, error ->
                videoQuizTraining.postValue(item)

                Log.i("ViewModelVideo", "fetchVideoTrainingData: $item")
            }
        }

    }




}