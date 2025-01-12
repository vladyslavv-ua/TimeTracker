package io.vladyslavv_ua.time_tracker.datasource.room.dao

import androidx.room.*
import io.vladyslavv_ua.time_tracker.entity.Project
import io.vladyslavv_ua.time_tracker.entity.ProjectWithTimeLaps
import io.vladyslavv_ua.time_tracker.entity.TimeLap
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDAO {
    @Transaction
    @Insert
    suspend fun addProject(project: Project)

    @Query("select * from project order by updatedAt desc")
    fun allProjects(): Flow<List<Project>>

    @Transaction
    @Query("select * from project where id = :id")
    fun findProject(id: Long): Flow<ProjectWithTimeLaps?>

    @Query("DELETE FROM project where id = :id")
    suspend fun deleteProject(id: Long)

    @Query("UPDATE project SET name = :name, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateProjectName(id: Long, name: String, updatedAt: String)

    @Query("UPDATE project SET updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateProjectUpdatedAt(id: Long, updatedAt: String)

}