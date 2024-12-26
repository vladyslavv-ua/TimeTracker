package io.vladyslavv_ua.time_tracker.datasource.room.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.room.RoomRawQuery

@Dao
interface DBManagerDAO {
    @RawQuery
    suspend fun commit(query: RoomRawQuery): Any?

}