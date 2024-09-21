package com.xcaret.loyaltyreps.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xcaret.loyaltyreps.view.general.ui.DialogLoadingFragment


open class ActivityBase : AppCompatActivity() {
    private val loadingDialog: DialogLoadingFragment? by lazy {
        DialogLoadingFragment.newInstance(Bundle.EMPTY)
    }

    fun showLoading() {
        loadingDialog?.show(supportFragmentManager, "Loading Dialog")
    }
    fun hideLoading(){
        loadingDialog?.dismiss()
    }
}