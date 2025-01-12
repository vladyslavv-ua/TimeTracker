package io.vladyslavv_ua.time_tracker.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "time_lap",
    foreignKeys = [ForeignKey(
        entity = Project::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("projectId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TimeLap(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(index = true)
    val projectId: Long,
    val label: String = "",
    val startedAt: LocalDateTime,
    val endedAt: LocalDateTime? = null,
)
