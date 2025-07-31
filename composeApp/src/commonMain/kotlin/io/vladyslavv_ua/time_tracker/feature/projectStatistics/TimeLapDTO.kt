package io.vladyslavv_ua.time_tracker.feature.projectStatistics

import kotlinx.datetime.LocalDateTime

data class TimeLapDTO(
    val startedAt: LocalDateTime,
    val endedAt: LocalDateTime,
    val usedTime: Long,
    val startedAtString: String,

)
