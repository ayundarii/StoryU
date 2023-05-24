package com.dicoding.storyu.di

import com.dicoding.storyu.data.repository.UserRepository
import com.dicoding.storyu.data.source.UserDataSource
import org.koin.dsl.module

val sourceModule = module {
    factory { UserRepository(get()) }
    single { UserDataSource(get(), get()) }
}