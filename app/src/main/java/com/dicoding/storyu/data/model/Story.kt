package com.dicoding.storyu.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
data class Story (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val description: String,
    @ColumnInfo("photo_url")
    val photoUrl: String,
    @ColumnInfo("created_at")
    val createdAt: String
)