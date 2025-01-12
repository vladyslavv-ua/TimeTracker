package io.vladyslavv_ua.time_tracker.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class ProjectWithTimeLaps(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectId"
    )
    val timeLaps: List<TimeLap>
)

