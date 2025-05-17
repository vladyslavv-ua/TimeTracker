package io.vladyslavv_ua.time_tracker.feature.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.vladyslavv_ua.time_tracker.entity.TimeLap
import io.vladyslavv_ua.time_tracker.globalInterface.IViewModelMVI
import io.vladyslavv_ua.time_tracker.repo.ProjectRepo
import io.vladyslavv_ua.time_tracker.repo.TimeLapRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ProjectViewModel(
    private val projectId: Long,
    private val projectRepo: ProjectRepo,
    private val timeLapRepo: TimeLapRepo
) : ViewModel(), IViewModelMVI<ProjectIntent> {
    private val _projectState = MutableStateFlow(ProjectState())
    val projectState: StateFlow<ProjectState> = _projectState

    private var isProjectLastUseUpdated: Boolean = false

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val project = projectRepo.getProjectInfo(projectId)
            project.collect { newProject ->
                _projectState.value = _projectState.value.copy(projectWithTimeLaps = newProject)
            }
        }
    }

    override fun onIntent(intent: ProjectIntent) {
        when (intent) {
            is ProjectIntent.CompleteLoop -> {
                completeLoop()
            }

            is ProjectIntent.NewLoop -> {
                newLoop()
            }

            is ProjectIntent.DeleteTimeLap -> {
                deleteTimeLap(intent.id)
            }

        }
    }

    private fun updateProjectLastUse() {
        if (!isProjectLastUseUpdated) {
            viewModelScope.launch(Dispatchers.Default) {
                projectRepo.updateProjectLastUse(projectId)
            }
        }

    }

    private fun completeLoop() {
        val timeLoop = projectState.value.projectWithTimeLaps?.timeLaps?.last()
        viewModelScope.launch {
            timeLapRepo.updateTimeLap(
                timeLoop!!.copy(
                    endedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
            )
        }
        _projectState.update {
            it.copy(
                lastIntent = ProjectIntent.CompleteLoop,
            )
        }
        updateProjectLastUse()
    }

    private fun newLoop() {
        viewModelScope.launch {
            timeLapRepo.addTimeLap(
                TimeLap(
                    id = 0,
                    projectId = projectId,
                    startedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                )
            )
        }
        updateProjectLastUse()
        _projectState.update {
            it.copy(
                lastIntent = ProjectIntent.NewLoop,
            )
        }
    }

    private fun deleteTimeLap(timeLapId: Long) {
        viewModelScope.launch {
            timeLapRepo.deleteTimeLap(timeLapId)
            _projectState.update {
                it.copy(
                    lastIntent = ProjectIntent.DeleteTimeLap(timeLapId),
                )
            }
        }
    }

}