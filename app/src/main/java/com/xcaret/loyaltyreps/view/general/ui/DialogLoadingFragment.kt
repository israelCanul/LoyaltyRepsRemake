package com.xcaret.loyaltyreps.view.general.ui

import android.os.Bundle
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.view.base.BaseDialogFragment


class DialogLoadingFragment : BaseDialogFragment() {

    override fun getLayout(): Int = R.layout.dialog_loading



    companion object {
        const val tag = "DialogLoadingFragment"
        lateinit var mArguments: Bundle
        fun newInstance(args: Bundle): DialogLoadingFragment {
            mArguments = args
            val fragment = DialogLoadingFragment()
            fragment.arguments = mArguments
            return fragment
        }
    }

}