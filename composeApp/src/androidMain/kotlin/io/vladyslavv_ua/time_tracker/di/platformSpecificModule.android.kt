package io.vladyslavv_ua.time_tracker.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import io.vladyslavv_ua.time_tracker.datasource.room.AppDB
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformSpecificModule: Module
    get() = module {
        single {
            getDatabaseBuilder(get())
        }
    }

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDB> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<AppDB>(
        context = appContext,
        name = dbFile.absolutePath
    )
}