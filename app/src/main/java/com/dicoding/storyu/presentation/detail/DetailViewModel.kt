package com.dicoding.storyu.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.repository.StoryRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: StoryRepository): ViewModel() {

    val detailResult: LiveData<ApiResponse<Story>> by lazy { _detailResult }
    private val _detailResult = MutableLiveData<ApiResponse<Story>>()

    fun getStoryDetail(id: String) {
        viewModelScope.launch {
            repository.getStoryDetail(id).collect {
                _detailResult.postValue(it)
            }
        }
    }
}