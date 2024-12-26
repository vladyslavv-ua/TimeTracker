package io.vladyslavv_ua.time_tracker.datasource.room.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime


class DateConverter {
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(value) }
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.toString()
    }
}