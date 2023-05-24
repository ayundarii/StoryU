package com.dicoding.storyu.data.network.request

import retrofit2.http.Field

data class RegisterRequest(
    @Field("name")
    val name: String,
    @Field("email")
    val email: String,
    @Field("password")
    val password: String
)