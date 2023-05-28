package com.dicoding.storyu.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.repository.StoryRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: StoryRepository): ViewModel()  {

    val storyResult: LiveData<ApiResponse<List<Story>>> by lazy { _storyResult }
    private val _storyResult = MutableLiveData<ApiResponse<List<Story>>>()

    fun getStories() {
        viewModelScope.launch {
            repository.getStories(true).collect {
                _storyResult.postValue(it)
            }
        }
    }
}