package io.vladyslavv_ua.time_tracker.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.vladyslavv_ua.time_tracker.datasource.room.AppDB
import io.vladyslavv_ua.time_tracker.repo.ProjectRepo
import io.vladyslavv_ua.time_tracker.repo.TimeLapRepo
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val sharedSingleton = module {
    single {
        getRoomDatabase(get())
    }
    single {
        ProjectRepo(get())
    }
    single {
        TimeLapRepo(get())
    }
}

private fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDB>
): AppDB {
    println("db initialized")
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}