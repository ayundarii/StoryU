package com.dicoding.storyu.data.network.services

import com.dicoding.storyu.data.network.response.BasicResponse
import com.dicoding.storyu.data.network.response.StoryDetailResponse
import com.dicoding.storyu.data.network.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryService {
    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = 0
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getStoryDetail(
        @Path("id") id: String
    ): StoryDetailResponse

    @POST("stories")
    @Multipart
    suspend fun addStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") latitude: RequestBody? = null,
        @Part("lon") longitude: RequestBody? = null,
    ): BasicResponse
}