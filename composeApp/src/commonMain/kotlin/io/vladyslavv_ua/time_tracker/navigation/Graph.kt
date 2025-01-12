package io.vladyslavv_ua.time_tracker.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Graph(val destination: String) {
    @Serializable
    data object Projects : Graph("projects")

    @Serializable
    data class Project(val id: Long) : Graph("project")

    @Serializable
    data class TimeLap(val id: Long) : Graph("time_lap")

    @Serializable
    data object FullStatistics : Graph("full_statistics")
}