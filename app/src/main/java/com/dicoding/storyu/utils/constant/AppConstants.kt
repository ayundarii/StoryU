package com.dicoding.storyu.utils.constant

import android.Manifest
object AppConstants {
    const val PREFS_NAME = "storyu.pref"
    const val KEY_TOKEN = "key.token"
    const val KEY_USER_ID = "key.user.id"
    const val AUTHORIZATION = "Authorization"

    val LOCATION_PERMISSION = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}