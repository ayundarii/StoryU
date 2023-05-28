package com.dicoding.storyu.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.storyu.data.local.dao.RemoteKeysDao
import com.dicoding.storyu.data.local.dao.StoryDao
import com.dicoding.storyu.data.model.RemoteKeys
import com.dicoding.storyu.data.model.Story

@Database(entities = [Story::class, RemoteKeys::class], version = 2, exportSchema = false)
abstract class StoryUDatabase : RoomDatabase() {
    abstract fun getStoryDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}