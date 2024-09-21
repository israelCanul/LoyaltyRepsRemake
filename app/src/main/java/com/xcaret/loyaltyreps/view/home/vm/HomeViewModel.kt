package com.xcaret.loyaltyreps.view.home.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.usecase.UserUseCase

import kotlinx.coroutines.*

class HomeViewModel: ViewModel() {
    val useCase = UserUseCase()
    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var currentUser = MutableLiveData<XUser?>()

}