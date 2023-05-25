package com.dicoding.storyu.data.network.services

import com.dicoding.storyu.data.network.request.LoginRequest
import com.dicoding.storyu.data.network.request.RegisterRequest
import com.dicoding.storyu.data.network.response.LoginResponse
import com.dicoding.storyu.data.network.response.BasicResponse
import retrofit2.http.*

interface UserService {
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): BasicResponse
}