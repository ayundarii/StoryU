package com.dicoding.storyu.data.network.response

import com.dicoding.storyu.data.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val error: Boolean,
    val message: String,
    @SerializedName("loginResult")
    val data: User
)