package com.xcaret.loyaltyreps.view.general.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.data.api.TrainingDetail
import com.xcaret.loyaltyreps.data.api.TrainingImagesSection
import com.xcaret.loyaltyreps.data.api.TrainingSection
import com.xcaret.loyaltyreps.data.entity.GalleryItem
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.usecase.UserUseCase
import com.xcaret.loyaltyreps.data.utils.Session
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {
    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val useCase = UserUseCase()
    var currentUser = MutableLiveData<XUser?>()
    var trainingExtrasParkDetail = MutableLiveData<TrainingDetail?>(null)
    var gallerySelected = MutableLiveData<List<GalleryItem>>(listOf())

    fun setTrainingDetail(xTraining: TrainingDetail){
        trainingExtrasParkDetail.value = xTraining
    }
    fun setTrainingSection(listItems: List<GalleryItem>){
        gallerySelected.value = listItems
    }

    fun setUser(){
        uiScope.launch {
            //create coroutine wthout blocking the current context
            //get value of current user
            currentUser.value = getUserInformationFromDatabase()
            currentUser?.let{ currentXUser ->
                currentXUser.value?.let{
                    Session.xuser_status = it.estatus
                    Session.setRepID( it.idRep,getApp().mContext)
                    Session.setRCX(it.rcx,getApp().mContext)

                }
            }
        }
    }
    private suspend fun getUserInformationFromDatabase(): XUser? {
        return withContext(Dispatchers.IO) {
            val xUser = useCase.getCurrent()
            xUser
        }
    }
    fun fetchUserDetailAPI(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("API ", "loadUserDataFromServerApi")
            UserUseCase().loadUserDataFromServerApi(Session.getRepID(getApp().mContext)){
                    user, error ->
                user?.id = 1
                var idrep = useCase.getDao().update(user!!)
                viewModelScope.launch(Dispatchers.Main) {
                    currentUser.postValue(user)
                }
            }
        }
    }
    fun getUser():XUser?{
        return currentUser.value
    }
    fun updateUser(xUser: XUser){
        viewModelScope.launch(Dispatchers.IO) {
            xUser?.id = 1
            val idrep = useCase.getDao().update(xUser)
            withContext(Dispatchers.Main) {
                Log.i("API", "Usuario actualizado $idrep")

            }
        }
    }
}