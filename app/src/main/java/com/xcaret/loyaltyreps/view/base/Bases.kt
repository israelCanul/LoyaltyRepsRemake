package com.xcaret.loyaltyreps.view.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.xcaret.loyaltyreps.R

class ServerErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setTitle(R.string.server_error_dialog_title)
            it.setMessage(R.string.server_error_dialog_message)
            it.setPositiveButton(R.string.server_error_dialog_button_caption) { _, _ -> dismiss() }
            it.create()
        }
    }

    companion object {
        fun newInstance(): ServerErrorDialogFragment {
            return ServerErrorDialogFragment()
        }
    }
}