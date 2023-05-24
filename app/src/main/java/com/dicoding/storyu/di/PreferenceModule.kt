package com.dicoding.storyu.di

import com.dicoding.storyu.utils.PreferenceManager
import org.koin.dsl.module

val preferenceModule = module {
    single { PreferenceManager(get()) }
}