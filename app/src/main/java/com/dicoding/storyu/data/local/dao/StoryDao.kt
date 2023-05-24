package com.dicoding.storyu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.storyu.data.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStories(stories: List<Story>)

    @Query("SELECT * FROM story")
    fun getAllStories(): List<Story>
}