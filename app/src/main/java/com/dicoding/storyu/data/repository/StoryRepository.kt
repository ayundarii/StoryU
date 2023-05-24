package com.dicoding.storyu.data.repository

import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.source.StoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class StoryRepository(
    private val dataSource: StoryDataSource
){
    suspend fun getStories(): Flow<ApiResponse<List<Story>>> {
        return dataSource.getStories().flowOn(Dispatchers.IO)
    }

}