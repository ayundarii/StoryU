package com.dicoding.storyu.di

import android.app.Application
import androidx.room.Room
import com.dicoding.storyu.BuildConfig
import com.dicoding.storyu.data.local.database.StoryUDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { provideUserService(get()) }

    factory { get<StoryUDatabase>().getStoryDao() }

    fun provideDatabase(application: Application): StoryUDatabase {
        return Room.databaseBuilder(application, StoryUDatabase::class.java, BuildConfig.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideDatabase(androidApplication()) }
}