package com.solodev.gweatherapp

import android.app.Application
import com.solodev.gweatherapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GWeatherApp)
            modules(appModule)
        }
    }
}