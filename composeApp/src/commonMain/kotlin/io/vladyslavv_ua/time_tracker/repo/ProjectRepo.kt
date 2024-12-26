package io.vladyslavv_ua.time_tracker.repo

import io.vladyslavv_ua.time_tracker.datasource.room.AppDB
import io.vladyslavv_ua.time_tracker.entity.Project
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ProjectRepo(appDB: AppDB) {
    private val projectDao = appDB.projectDao()
    suspend fun addProject(project: Project) = projectDao.addProject(project)
    fun getProjects() = projectDao.allProjects()
    fun getProjectInfo(id: Long) = projectDao.findProject(id)
    suspend fun deleteProject(id: Long) = projectDao.deleteProject(id)
    suspend fun updateProjectName(id: Long, name: String) {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        projectDao.updateProjectName(id, name, currentDate.toString())
    }

    suspend fun updateProjectLastUse(id: Long) = projectDao.updateProjectUpdatedAt(
        id, Clock.System.now().toLocalDateTime(
            TimeZone
                .currentSystemDefault()
        ).toString()
    )
}