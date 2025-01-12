package io.vladyslavv_ua.time_tracker.di

import androidx.lifecycle.viewmodel.compose.viewModel
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
            getDatabaseBuilder()
        }

        factory {
            val file = File(System.getProperty("user.dir"), "my_room.db")
            FullStatisticsScreenVM(file)
        }


    }


fun getDatabaseBuilder(): RoomDatabase.Builder<AppDB> {
    val dbFile = File(System.getProperty("user.dir"), "my_room.db")
    println(System.getProperty("user.dir"))
    return Room.databaseBuilder<AppDB>(
        name = dbFile.absolutePath,
    )
}
