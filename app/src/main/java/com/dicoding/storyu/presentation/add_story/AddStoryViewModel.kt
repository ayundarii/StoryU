package com.dicoding.storyu.presentation.add_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.repository.StoryRepository
import kotlinx.coroutines.launch
import java.io.File

class AddStoryViewModel(private val repository: StoryRepository): ViewModel() {

    private val _addStoryResult = MutableLiveData<ApiResponse<String>>()
    val addStoryResult: LiveData<ApiResponse<String>> = _addStoryResult

    fun resetAddStoryResult() {
        _addStoryResult.value = ApiResponse.Empty
    }

    fun addStory(
        photo: File,
        description: String,
        latitude: Float? = null,
        longitude: Float? = null
    ) {
        viewModelScope.launch {
            repository.addStory(photo, description, latitude, longitude).collect {
                _addStoryResult.postValue((it))
            }
        }
    }
}