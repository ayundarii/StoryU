package com.dicoding.storyu.data.source

import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.network.services.StoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class StoryDataSource(
    private val service: StoryService
) {
    suspend fun getStories(): Flow<ApiResponse<List<Story>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.getStories()

                if (response.listStory.isEmpty()) {
                    emit(ApiResponse.Empty)
                    return@flow
                }

                emit(ApiResponse.Success(response.listStory))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

}