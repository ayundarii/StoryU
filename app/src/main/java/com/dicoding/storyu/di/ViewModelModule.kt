package com.dicoding.storyu.di

import com.dicoding.storyu.presentation.add_story.AddStoryViewModel
import com.dicoding.storyu.presentation.detail.DetailViewModel
import com.dicoding.storyu.presentation.home.HomeViewModel
import com.dicoding.storyu.presentation.login.LoginViewModel
import com.dicoding.storyu.presentation.register.RegisterViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { HomeViewModel(get()) }
    single { DetailViewModel(get()) }
    single { AddStoryViewModel(get()) }
}