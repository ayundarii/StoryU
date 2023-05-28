package com.dicoding.storyu.paging_story

import com.dicoding.storyu.data.model.Story

object DummyData {

    fun listStoryDummy(): List<Story> {
        val items = arrayListOf<Story>()
        for (i in 0 until 3) {
            val story = Story(
                id = "story-jUum3cHSSWBXYpwc",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1685232544982_-sHxIEAZ.jpg",
                createdAt = "2023-05-28T00:00:00.176Z",
                name = "Username",
                description = "Description",
                latitude = (48.8566).toFloat(),
                longitude = (2.3522).toFloat(),
            )
            items.add(story)
        }
        return items
    }

    fun emptyListStoryDummy(): List<Story> = emptyList()


}