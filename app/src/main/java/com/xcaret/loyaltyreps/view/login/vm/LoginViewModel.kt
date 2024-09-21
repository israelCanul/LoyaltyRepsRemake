package com.xcaret.loyaltyreps.view.login.vm

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.usecase.UserUseCase
import com.xcaret.loyaltyreps.data.utils.Session
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel(){
    var mUser: MutableLiveData<XUser> = MutableLiveData<XUser>()
    val userCase : UserUseCase = UserUseCase()
    var errorLogin: MutableLiveData<String> = MutableLiveData<String>("")
    var errorRetrieve: MutableLiveData<String> = MutableLiveData<String>("")
    var successRetrieve: MutableLiveData<String> = MutableLiveData<String>("")

    fun saveODB(user: XUser){
        viewModelScope.launch {
            userCase.getDao().insert(user)
        }
    }

    fun login(email: String, password: String){
            userCase.login(email,password){ user, error ->

                user?.let{

                    Session.loggedIn = true // se cambia las preferecias a Logeado
                    mUser.postValue(it)
                }?:run {
                    errorLogin.postValue(ContextCompat.getString(getApp().mContext, R.string.error_login_credentials))
                    Log.i("API", "${error?.message}")
                }
            }
    }

    fun retrieveEmail(rcx: String){
        userCase.retrievePassword(rcx){ success, error ->
            Log.i("API", "$success $error")
            if(success !== null){
                successRetrieve.postValue("success")
            }else{
                error?.let{
                    errorRetrieve.postValue(it)
                }
            }
        }
    }

}