package com.xcaret.loyaltyreps.view.login.ui

import android.os.Bundle
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.view.base.BaseDialogFragment


class DialogSuccessRetrievePass : BaseDialogFragment() {
    override fun getLayout(): Int = R.layout.dialog_retrieve_pass_succes


    companion object {
        const val tag = "DialogSuccessRetrievePass"
        lateinit var mArguments: Bundle
        fun newInstance(args: Bundle): DialogSuccessRetrievePass {
            mArguments = args
            val fragment = DialogSuccessRetrievePass()
            fragment.arguments = mArguments
            return fragment
        }
    }
}