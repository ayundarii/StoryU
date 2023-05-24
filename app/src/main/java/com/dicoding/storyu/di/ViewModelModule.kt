package com.dicoding.storyu.di

import com.dicoding.storyu.presentation.home.HomeViewModel
import com.dicoding.storyu.presentation.login.LoginViewModel
import com.dicoding.storyu.presentation.register.RegisterViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { LoginViewModel(get()) }
    single { HomeViewModel(get()) }
}