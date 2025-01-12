package io.vladyslavv_ua.time_tracker.feature.projects

import io.vladyslavv_ua.time_tracker.entity.Project
import io.vladyslavv_ua.time_tracker.globalInterface.IINtent

data class ProjectsState(
    val projects: List<Project> = emptyList(),
    val searchState: String = "",
    val isModalShowed: Boolean = false,
    val projectName: String = "",
    val updateProjectNameField: String = "",
)

sealed class ProjectsIntent : IINtent {
    data object ToggleModalVisibility : ProjectsIntent()
    data class UpdateCreateProjectField(val projectName: String) : ProjectsIntent()
    data object CreateProject : ProjectsIntent()
    data class DeleteProject(val projectId: Long) : ProjectsIntent()
    data class UpdateProjectNameField(val newName: String) : ProjectsIntent()
    data class UpdateProjectName(val id:Long, val name:String): ProjectsIntent()
}