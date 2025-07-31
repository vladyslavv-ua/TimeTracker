package io.vladyslavv_ua.time_tracker.datasource.room.dao

import androidx.room.*
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

    @Query("DELETE FROM time_lap WHERE id = :id")
    suspend fun deleteTimeLap(id: Long)

    @Query("SELECT * FROM time_lap WHERE projectId=:projectId")
    suspend fun getAllTimeLapsFromProject(projectId: Long): List<TimeLap>
}