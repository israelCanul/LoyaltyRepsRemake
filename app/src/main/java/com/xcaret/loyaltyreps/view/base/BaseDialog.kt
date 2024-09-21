package com.xcaret.loyaltyreps.view.base

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.DialogFragment
import com.xcaret.loyaltyreps.R


abstract class BaseDialogFragment : DialogFragment(){
    abstract fun getLayout(): Int
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }
    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }
    fun setTransitionStyle(resource: Int){
        dialog?.window?.attributes?.windowAnimations = resource
    }
    override fun onDestroyView() {
        dialog?.setDismissMessage(null)
        super.onDestroyView()
    }
    fun restartApp() {
        activity.let { act ->
            val intent = activity?.intent  //Intent(act, ItemRenderActivity::class.java)
            startActivity(intent!!)
            act?.finish()

            val mPendingIntentId  = 123456
            val mPendingIntent = PendingIntent.getActivity(
                act,
                mPendingIntentId,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val mgr = act?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            mgr[AlarmManager.RTC, System.currentTimeMillis() + 10] = mPendingIntent
        }
    }
}