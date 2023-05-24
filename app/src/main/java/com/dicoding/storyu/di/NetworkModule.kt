package com.dicoding.storyu.di

import com.dicoding.storyu.BuildConfig
import com.dicoding.storyu.data.network.services.HeaderInterceptor
import com.dicoding.storyu.data.network.services.StoryService
import com.dicoding.storyu.data.network.services.UserService
import com.dicoding.storyu.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        return@single OkHttpClient.Builder()
            .addInterceptor(getHeaderInterceptor(get()))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { provideUserService(get()) }
    single { provideStoryService(get()) }
}

private val loggingInterceptor = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}

private fun getHeaderInterceptor(preferenceManager: PreferenceManager): Interceptor {
    val headers = HashMap<String, String>()
    headers["Content-Type"] = "application/json"
    return HeaderInterceptor(headers, preferenceManager)
}

fun provideUserService(retrofit: Retrofit): UserService =
    retrofit.create(UserService::class.java)

fun provideStoryService(retrofit: Retrofit): StoryService =
    retrofit.create(StoryService::class.java)