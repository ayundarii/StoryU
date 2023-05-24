package com.dicoding.storyu.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.repository.StoryRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: StoryRepository): ViewModel() {

    val storiesResult: LiveData<ApiResponse<List<Story>>> by lazy { _storiesResult }
    private val _storiesResult = MutableLiveData<ApiResponse<List<Story>>>()

    fun getStories() {
        viewModelScope.launch {
            repository.getStories().collect {
                _storiesResult.postValue(it)
            }
        }
    }
}