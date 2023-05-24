package com.dicoding.storyu.utils.constant

import android.Manifest
object AppConstants {
    const val PREFS_NAME = "storyu.pref"
    const val KEY_TOKEN = "key.token"
    const val KEY_USER_ID = "key.user.id"
    const val KEY_ON_CLICK = "key.onclick"
    const val AUTHORIZATION = "Authorization"
    const val MULTIPART_FORM_DATA = "multipart/form-data"
    const val MULTIPART_FILE_NAME = "photo"
    const val TOAST_ACTION = "Toast.Action"

    val LOCATION_PERMISSION = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}