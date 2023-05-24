package com.dicoding.storyu.utils

import android.content.Context
import android.content.SharedPreferences
import com.dicoding.storyu.utils.constant.AppConstants.KEY_TOKEN
import com.dicoding.storyu.utils.constant.AppConstants.KEY_USER_ID
import com.dicoding.storyu.utils.constant.AppConstants.PREFS_NAME

class PreferenceManager(context: Context) {

    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun setCurrentUser(token: String, userId: String) {
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    fun clearAllPreferences() {
        editor.remove(KEY_TOKEN)
        editor.remove(KEY_USER_ID)
        editor.apply()
    }

    fun getToken(): String {
        return prefs.getString(KEY_TOKEN, "") ?: ""
    }

    fun getUserId(): String {
        return prefs.getString(KEY_USER_ID, "") ?: ""
    }

    val isLoggedIn = getToken().isNotEmpty()
}