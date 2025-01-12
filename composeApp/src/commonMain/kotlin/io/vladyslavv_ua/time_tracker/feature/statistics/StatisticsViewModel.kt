package io.vladyslavv_ua.time_tracker.feature.statistics

import androidx.lifecycle.ViewModel
import io.vladyslavv_ua.time_tracker.repo.ProjectRepo
import io.vladyslavv_ua.time_tracker.repo.TimeLapRepo

class StatisticsViewModel(
    private val projectId: Long,
    private val projectRepo: ProjectRepo,
    private val timeLapRepo: TimeLapRepo
) : ViewModel() {
}