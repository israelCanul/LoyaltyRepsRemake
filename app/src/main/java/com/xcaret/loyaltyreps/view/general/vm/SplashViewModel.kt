package com.xcaret.loyaltyreps.view.general.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.usecase.DownloadUseCase
import com.xcaret.loyaltyreps.data.usecase.DownloadUseCase.*
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel(){
    val currentUser = MutableLiveData(XUser())
    val resultUser = MutableLiveData(false)
    val downloadUseCase: DownloadUseCase by lazy { DownloadUseCase(downloadListener)}
    val resultDownload = MutableLiveData<ResultDownload?>()
    init {
    }

    fun load(){
        Log.i("OnSplashViewModel", "load: ")
        getCurrentUser()
        Log.i("OnSplashViewModel", "load: ${currentUser.value}")
        downloadUseCase.startDownload(TypeInfo.UserInfo.value)
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            val cUser: XUser? = downloadUseCase.getLocalUser()
            cUser?.let {
                resultUser.postValue(true)
                currentUser.postValue(cUser!!)
            }?:kotlin.run{
                resultUser.postValue(true)
                currentUser.postValue(null)
            }
        }
    }

    private val downloadListener = object: ResultListener {
        override fun onResult(result: ResultDownload) {
            Log.i("downloadListener result", " ${result.errorCode}")

            resultDownload.postValue(result)
        }
    }
}