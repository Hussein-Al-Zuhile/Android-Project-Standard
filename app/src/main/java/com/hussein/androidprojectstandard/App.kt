package com.hussein.androidprojectstandard

import android.app.Application
import com.hussein.androidprojectstandard.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {

            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)

            androidContext(this@App)

            modules(MainModule)
        }
    }
}