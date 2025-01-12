package io.vladyslavv_ua.time_tracker

import android.app.Application
import io.vladyslavv_ua.time_tracker.datasource.room.AppDB
import io.vladyslavv_ua.time_tracker.di.platformSpecificModule
import io.vladyslavv_ua.time_tracker.di.sharedSingleton
import io.vladyslavv_ua.time_tracker.di.sharedVM
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TimeTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TimeTrackerApp)
            androidLogger()
            modules(platformSpecificModule + sharedSingleton + sharedVM)
        }

    }

    override fun onTerminate() {
        super.onTerminate()
    }
}