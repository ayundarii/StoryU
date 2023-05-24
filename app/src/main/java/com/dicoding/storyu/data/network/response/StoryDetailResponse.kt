package com.dicoding.storyu.data.network.response

import com.dicoding.storyu.data.model.Story
import com.google.gson.annotations.SerializedName

data class StoryDetailResponse(
    val error: Boolean,
    val message: String,
    @SerializedName("story")
    val story: Story
)