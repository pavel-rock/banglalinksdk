package com.rockstreamer.iscreensdk.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.rockstreamer.iscreensdk.R

object CustomAlertDialog {

    fun showAlertDialog(activity: Activity): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.custom_progress_dialog, null))
        builder.setCancelable(false)
        var alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return alertDialog
    }
}
