package com.dicoding.storyu.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository): ViewModel() {

    private val _registerResult = MutableLiveData<ApiResponse<String>>()
    val registerResult: LiveData<ApiResponse<String>> = _registerResult

    fun resetRegisterResult() {
        _registerResult.value = ApiResponse.Empty
    }

    fun register(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            repository.register(
                name,
                email,
                password )
                .collect {
                    _registerResult.postValue(it)
                }
        }
    }
}