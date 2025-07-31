package io.vladyslavv_ua.time_tracker.feature.projectStatistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.vladyslavv_ua.time_tracker.entity.TimeLap
import io.vladyslavv_ua.time_tracker.globalInterface.IViewModelMVI
import io.vladyslavv_ua.time_tracker.repo.TimeLapRepo
import io.vladyslavv_ua.time_tracker.utli.normalizeMinMax
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Duration

class ProjectStatisticsViewModel(
    private val projectId: Long,
    private val timeLapRepo: TimeLapRepo
) : ViewModel(), IViewModelMVI<ProjectStatisticsIntent> {

    private val _state = MutableStateFlow(ProjectStatisticsState())
    val state = _state.asStateFlow()

    init {
        getTimeLaps()
    }

    override fun onIntent(intent: ProjectStatisticsIntent) {
        TODO("Not yet implemented")
    }


    private fun getTimeLaps() {
        viewModelScope.launch(Dispatchers.Default) {
            val timeLaps = timeLapRepo.getTimeLapsByProject(projectId)
            val groupedTimeLaps = timeLaps.filter { timeLap ->
                timeLap.endedAt != null
            }.map { timeLap ->
                val usedTime =
                    timeLap.endedAt!!.toInstant(TimeZone.currentSystemDefault()) - timeLap.startedAt.toInstant(TimeZone.currentSystemDefault())
                timeLap.toTimeLapDTO(usedTime.inWholeSeconds, timeLap.startedAt.date.toString())
            }.groupBy { timeLap ->
                timeLap.startedAtString
            }.mapValues { (_, timeLaps) ->
                timeLaps.fold(0L) { acc, timeLap -> acc + timeLap.usedTime }
            }


            _state.update { it.copy(timeLaps = groupedTimeLaps) }

        }
    }


}