package io.vladyslavv_ua.time_tracker.datasource.room

import androidx.room.*
import io.vladyslavv_ua.time_tracker.datasource.room.converter.DateConverter
import io.vladyslavv_ua.time_tracker.datasource.room.dao.DBManagerDAO
import io.vladyslavv_ua.time_tracker.datasource.room.dao.ProjectDAO
import io.vladyslavv_ua.time_tracker.datasource.room.dao.TimeLapDao
import io.vladyslavv_ua.time_tracker.entity.Project
import io.vladyslavv_ua.time_tracker.entity.TimeLap

@Database(entities = [Project::class, TimeLap::class], version = 1)
@TypeConverters(DateConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDB : RoomDatabase() {
    abstract fun projectDao(): ProjectDAO
    abstract fun timeLapDao(): TimeLapDao
    abstract fun dbManagerDao(): DBManagerDAO
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDB>