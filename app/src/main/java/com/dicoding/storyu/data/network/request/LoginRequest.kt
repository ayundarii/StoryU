package com.dicoding.storyu.data.network.request

import retrofit2.http.Field

data class LoginRequest(
    @Field("email")
    val email: String,
    @Field("password")
    val password: String
)