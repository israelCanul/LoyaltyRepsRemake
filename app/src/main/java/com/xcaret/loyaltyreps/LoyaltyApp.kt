package com.xcaret.loyaltyreps

import android.app.Application
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.database
import com.xcaret.loyaltyreps.data.config.Settings
import com.xcaret.loyaltyreps.data.config.Settings.ID_USER_API_VALUE
import com.xcaret.loyaltyreps.data.config.Settings.PUNK_API_TOKEN
import com.xcaret.loyaltyreps.data.config.Settings.PUNK_API_TOKEN_VALUE
import com.xcaret.loyaltyreps.data.config.Settings.idUsuarioApi
import com.xcaret.loyaltyreps.data.utils.AppPreferences


class LoyaltyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        AppPreferences.init(this)
        setupFirebase()

        Settings.setParam(PUNK_API_TOKEN, PUNK_API_TOKEN_VALUE, getApp().mContext)
        Settings.setParam(idUsuarioApi, ID_USER_API_VALUE, getApp().mContext)
    }

    private fun setupFirebase(){
        FirebaseApp.initializeApp(mContext)
        if(!BuildConfig.DEBUG) Firebase.database.setPersistenceEnabled(true)
    }


    companion object{
        lateinit var mContext: Context
        fun getApp() = this
    }
}