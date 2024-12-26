package io.vladyslavv_ua.time_tracker.feature.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.vladyslavv_ua.time_tracker.entity.Project
import io.vladyslavv_ua.time_tracker.globalInterface.IViewModelMVI
import io.vladyslavv_ua.time_tracker.repo.ProjectRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectsViewModel(private val projectRepo: ProjectRepo) : ViewModel(), IViewModelMVI<ProjectsIntent> {
    private val _projectsState = MutableStateFlow(ProjectsState())
    val projectsState: StateFlow<ProjectsState> = _projectsState

    init {
        viewModelScope.launch(Dispatchers.Default) {
            projectRepo.getProjects().collect {
                _projectsState.value = _projectsState.value.copy(projects = it)
            }
        }
    }

    override fun onIntent(intent: ProjectsIntent) {
        when (intent) {
            is ProjectsIntent.ToggleModalVisibility -> {
                toggleModalVisibility()
            }

            is ProjectsIntent.UpdateCreateProjectField -> {
                updateCreateProjectField(intent.projectName)
            }

            is ProjectsIntent.CreateProject -> {
                createProject()
            }

            is ProjectsIntent.DeleteProject -> {
                deleteProject(intent.projectId)
            }

            is ProjectsIntent.UpdateProjectNameField -> {
                updateProjectNameField(intent.newName)
            }

            is ProjectsIntent.UpdateProjectName -> {
                updateProjectName(intent.id, intent.name)
            }
        }
    }

    private fun updateProjectName(id: Long, name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            projectRepo.updateProjectName(id, name)
        }
    }

    private fun updateProjectNameField(newName: String) {
        _projectsState.update { it.copy(updateProjectNameField = newName) }
    }

    private fun deleteProject(projectId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            projectRepo.deleteProject(projectId)
        }
    }

    private fun toggleModalVisibility() {
        _projectsState.value = _projectsState.value.copy(isModalShowed = !_projectsState.value.isModalShowed)
    }

    private fun updateCreateProjectField(newValue: String) {
        _projectsState.update {
            it.copy(projectName = newValue)
        }
    }

    private fun createProject() {
        val project = Project(name = _projectsState.value.projectName)
        viewModelScope.launch {
            projectRepo.addProject(project)
            _projectsState.value = _projectsState.value.copy(isModalShowed = false, projectName = "")
        }
    }

}