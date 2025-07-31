package io.vladyslavv_ua.time_tracker.feature.projectStatistics

import io.vladyslavv_ua.time_tracker.entity.TimeLap
import io.vladyslavv_ua.time_tracker.globalInterface.IINtent
import kotlinx.datetime.LocalDate
import kotlin.time.Duration

data class ProjectStatisticsState(
    val timeLaps: Map<String, Long> = emptyMap(),
)

sealed class ProjectStatisticsIntent: IINtent {}