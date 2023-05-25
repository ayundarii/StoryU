package com.dicoding.storyu.base.custom_view

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.dicoding.storyu.R

internal class CustomLoadingDialog(context: Context): Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY)
        setCancelable(false)
        setContentView(R.layout.loading_dialog)
    }

    fun showDialog() {
        show()
    }

    fun dismissDialog() {
        dismiss()
    }
}