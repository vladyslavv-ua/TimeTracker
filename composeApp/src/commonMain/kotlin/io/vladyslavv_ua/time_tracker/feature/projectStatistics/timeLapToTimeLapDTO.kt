package io.vladyslavv_ua.time_tracker.feature.projectStatistics

import io.vladyslavv_ua.time_tracker.entity.TimeLap

fun TimeLap.toTimeLapDTO(lapDuration: Long, label:String) = TimeLapDTO(
    startedAt,
    endedAt!!,
    lapDuration,
    label,
)