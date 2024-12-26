package io.vladyslavv_ua.time_tracker.feature.fullStatistics

import io.vladyslavv_ua.time_tracker.entity.DataChart

data class FullStatisticsState(
    val fullWastedTime: Long = 0L,
    val categoryDuration:  List<DataChart> = emptyList(),
    val averageSpentTime: List<DataChart> = emptyList(),
)