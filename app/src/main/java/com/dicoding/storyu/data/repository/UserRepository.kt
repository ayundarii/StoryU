package com.dicoding.storyu.data.repository

import com.dicoding.storyu.data.network.request.LoginRequest
import com.dicoding.storyu.data.network.request.RegisterRequest
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.source.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val dataSource: UserDataSource
){

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<String>> {
        return dataSource.register(
            RegisterRequest(
                name,
                email,
                password )
        ).flowOn(Dispatchers.IO)
    }

    suspend fun login(
        email: String,
        password: String
    ): Flow<ApiResponse<String>> {
        return dataSource.login(
            LoginRequest(
                email,
                password )
        ).flowOn(Dispatchers.IO)
    }

}