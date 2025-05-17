package io.vladyslavv_ua.time_tracker.feature.project

import io.vladyslavv_ua.time_tracker.entity.ProjectWithTimeLaps
import io.vladyslavv_ua.time_tracker.globalInterface.IINtent

data class ProjectState(
    val projectWithTimeLaps: ProjectWithTimeLaps? = null,
    val isSuccess: Boolean = true,
    val lastIntent: ProjectIntent = ProjectIntent.NewLoop
)

sealed class ProjectIntent : IINtent {
    data object CompleteLoop : ProjectIntent()
    data object NewLoop : ProjectIntent()
    data class DeleteTimeLap(val id: Long) : ProjectIntent()
}