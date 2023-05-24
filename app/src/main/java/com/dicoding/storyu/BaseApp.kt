package com.dicoding.storyu

import android.app.Application
import com.dicoding.storyu.di.networkModule
import com.dicoding.storyu.di.preferenceModule
import com.dicoding.storyu.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApp)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    preferenceModule
                )

            )
        }
    }
}