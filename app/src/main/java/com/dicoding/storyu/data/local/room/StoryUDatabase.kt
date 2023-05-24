package com.dicoding.storyu.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.storyu.data.local.dao.StoryDao
import com.dicoding.storyu.data.model.Story

@Database(entities = [Story::class], version = 1, exportSchema = false)
abstract class StoryUDatabase: RoomDatabase() {
    abstract fun getStoryDao(): StoryDao
}