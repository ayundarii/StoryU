package com.dicoding.storyu.data.source

import com.dicoding.storyu.data.network.request.LoginRequest
import com.dicoding.storyu.data.network.request.RegisterRequest
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.network.services.UserService
import com.dicoding.storyu.di.networkModule
import com.dicoding.storyu.di.preferenceModule
import com.dicoding.storyu.utils.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class UserDataSource(
    private val preference: PreferenceManager,
    private val service: UserService
) {

    suspend fun register(
        request: RegisterRequest
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.register(request)

                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                emit(ApiResponse.Success("SUCCESS : $response"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun login(
        request: LoginRequest
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.login(request)
                preference.setCurrentUser(
                    response.data.token,
                    response.data.id,
                    response.data.name)
                reloadModule()

                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                emit(ApiResponse.Success("SUCCESS : $response"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    private fun reloadModule() {
        unloadKoinModules(preferenceModule)
        loadKoinModules(preferenceModule)

        unloadKoinModules(networkModule)
        loadKoinModules(networkModule)
    }

}