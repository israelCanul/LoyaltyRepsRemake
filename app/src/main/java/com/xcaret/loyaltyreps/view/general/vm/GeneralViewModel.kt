package com.xcaret.loyaltyreps.view.general.vm

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.data.usecase.DownloaderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeneralViewModel : ViewModel() {
    var downloaderUseCase = DownloaderUseCase()
    fun downloadPdf(displayName: String, imgUrl: String, activity: Activity, final: ()->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            downloaderUseCase .downloaderPdf(getApp().mContext,activity, imgUrl, displayName,{
                final()
            }){

            }
        }
    }
}