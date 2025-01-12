package io.vladyslavv_ua.time_tracker.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import io.vladyslavv_ua.time_tracker.datasource.room.AppDB
import io.vladyslavv_ua.time_tracker.feature.fullStatistics.FullStatisticsScreenVM
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformSpecificModule: Module
    get() = module {
        single {
            getDatabaseBuilder(get())
        }
        factory {
//            val file = File("${getPackagePath(get())}/my_room.db")
            val file = getDatabasePath(get())
            println(file)
            FullStatisticsScreenVM(file)
        }
    }


fun getDatabasePath(ctx: Context): File {
    return ctx.applicationContext.getDatabasePath("my_room.db")

}

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDB> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("my_room.db")
    println("room" + dbFile)
    return Room.databaseBuilder<AppDB>(
        context = appContext,
        name = dbFile.absolutePath
    )
}