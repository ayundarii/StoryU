package com.dicoding.storyu.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("idUser")
    val id: String,
    val name: String,
    val token: String
)