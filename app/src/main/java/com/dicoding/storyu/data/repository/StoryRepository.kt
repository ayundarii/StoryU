package com.dicoding.storyu.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.source.StoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class StoryRepository(
    private val dataSource: StoryDataSource
){
    fun getPagingStories(): LiveData<PagingData<Story>> {
        return dataSource.getPagingStories()
    }

    suspend fun getStories(isLocationAvailable: Boolean): Flow<ApiResponse<List<Story>>> {
        return dataSource.getStories(isLocationAvailable).flowOn(Dispatchers.IO)
    }

    suspend fun getStoryDetail(id: String): Flow<ApiResponse<Story>> {
        return dataSource.getStoryDetail(id).flowOn(Dispatchers.IO)
    }

    suspend fun addStory(
        photo: File,
        description: String,
        latitude: Float? = null,
        longitude: Float? = null
    ): Flow<ApiResponse<String>> {
        return dataSource.addStory(
            photo,
            description,
            latitude,
            longitude
        ).flowOn(Dispatchers.IO)
    }

}