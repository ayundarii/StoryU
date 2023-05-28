package com.dicoding.storyu.presentation.home

import androidx.lifecycle.ViewModel
import com.dicoding.storyu.data.repository.StoryRepository

class HomeViewModel( private val storyRepository: StoryRepository ) : ViewModel() {
    fun getStory() = storyRepository.getPagingStories()
}