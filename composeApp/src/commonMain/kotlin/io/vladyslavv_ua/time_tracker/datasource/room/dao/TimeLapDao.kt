package io.vladyslavv_ua.time_tracker.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.vladyslavv_ua.time_tracker.entity.TimeLap

@Dao
interface TimeLapDao {

    @Insert
    suspend fun addTimeLap(timeLap: TimeLap)

    @Update
    suspend fun updateTimeLap(timeLap: TimeLap)

    @Query("SELECT * FROM time_lap WHERE id = :id")
    suspend fun getTimeLapById(id: Long): TimeLap?

    @Query("UPDATE time_lap SET label = :label WHERE id = :id")
    suspend fun updateTimeLapLabel(id: Long, label: String)
}