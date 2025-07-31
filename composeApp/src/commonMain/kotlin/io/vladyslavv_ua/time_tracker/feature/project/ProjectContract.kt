package io.vladyslavv_ua.time_tracker.feature.project

import io.vladyslavv_ua.time_tracker.entity.ProjectWithTimeLaps
import io.vladyslavv_ua.time_tracker.globalInterface.IINtent
import io.vladyslavv_ua.time_tracker.globalInterface.ISideEffect

data class ProjectState(
    val projectWithTimeLaps: ProjectWithTimeLaps? = null,
    val isSuccess: Boolean = true,
    val lastIntent: ProjectIntent = ProjectIntent.NewLoop
)

sealed class ProjectIntent : IINtent {
    data object CompleteLoop : ProjectIntent()
    data object NewLoop : ProjectIntent()
    data class DeleteTimeLap(val id: Long) : ProjectIntent()
    data object OpenProjectStatistics : ProjectIntent()
}

sealed class ProjectSideEffect : ISideEffect {
    data class OpenProject(val projectId: Long) : ProjectSideEffect()
}