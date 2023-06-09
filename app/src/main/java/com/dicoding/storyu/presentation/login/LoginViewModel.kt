package com.dicoding.storyu.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    private val _loginResult = MutableLiveData<ApiResponse<String>>()
    val loginResult: LiveData<ApiResponse<String>> = _loginResult

    fun resetLoginResult() {
        _loginResult.value = ApiResponse.Empty
    }

    fun login(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            repository.login(
                email,
                password )
                .collect {
                    _loginResult.postValue(it)
                }
        }
    }

}