package com.dicoding.storyu.data.source

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.dicoding.storyu.data.local.database.StoryUDatabase
import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.network.services.StoryService
import com.dicoding.storyu.presentation.mediator.StoryRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryDataSource(
    private val database: StoryUDatabase,
    private val service: StoryService
) {
    fun getPagingStories(): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 3
            ),
            remoteMediator = StoryRemoteMediator(database, service),
            pagingSourceFactory = {
                database.getStoryDao().getAllStories()
            }
        ).liveData
    }
    suspend fun getStories(isLocationAvailable: Boolean = false): Flow<ApiResponse<List<Story>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.getStories(location = if(isLocationAvailable) 1 else 0)

                if (response.listStory.isEmpty()) {
                    emit(ApiResponse.Empty)
                    return@flow
                }

                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                emit(ApiResponse.Success(response.listStory))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun getStoryDetail(id: String): Flow<ApiResponse<Story>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.getStoryDetail(id)

                emit(ApiResponse.Success(response.story))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun addStory(
        photo: File,
        description: String,
        latitude: Float? = null,
        longitude: Float? = null): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val requestImageFile = photo.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    photo.name,
                    requestImageFile
                )

                val latitudeRequestBody = latitude?.toString()?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val longitudeRequestBody = longitude?.toString()?.toRequestBody("multipart/form-data".toMediaTypeOrNull())

                val response = if (latitudeRequestBody != null && longitudeRequestBody != null) {
                    service.addStory(
                        imageMultipart,
                        description.toRequestBody("text/plain".toMediaType()),
                        latitudeRequestBody,
                        longitudeRequestBody
                    )
                } else {
                    service.addStory(
                        imageMultipart,
                        description.toRequestBody("text/plain".toMediaType())
                    )
                }

                emit(ApiResponse.Success(response.message))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

}