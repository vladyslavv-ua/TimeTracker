package io.vladyslavv_ua.time_tracker.feature.fullStatistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.vladyslavv_ua.time_tracker.entity.DataChart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.DbConnectionConfig
import org.jetbrains.kotlinx.dataframe.io.readSqlQuery
import java.io.File

class FullStatisticsScreenVM(file: File) : ViewModel() {
    //    private val file = File(System.getProperty("user.dir"), "my_room.db")
    private val connection = DbConnectionConfig("jdbc:sqlite:$file")

    private val _state = MutableStateFlow(FullStatisticsState())
    val state: StateFlow<FullStatisticsState> = _state

    init {

        println(file.absolutePath)

        viewModelScope.launch {

            val joinedTimeLaps =
                DataFrame.readSqlQuery(
                    connection,
                    "select time_lap.id, project.name, time_lap.startedAt, time_lap.endedAt from time_lap join project on time_lap.projectId = project.id where time_lap.endedAt NOT NULL"
                )

            val withDurations =
                joinedTimeLaps.convert("startedAt", "endedAt") {
                    LocalDateTime.parse((it?.toString() ?: 0).toString())
                }.add("duration") {
                    ((getValue("endedAt") as LocalDateTime).toInstant(TimeZone.currentSystemDefault()) - (getValue("startedAt") as LocalDateTime).toInstant(
                        TimeZone.currentSystemDefault()
                    )).inWholeSeconds
                }

//            println(withDurations.columnTypes())
//
            val some = withDurations.groupBy(*listOf("name").toTypedArray()).updateGroups {
                val sum = mean(*listOf("duration").toTypedArray())
                add("proportion") { sum }
            }.concat()

//            // Обчислення середнього і загального часу
//            val totalDuration = withDurations.sumOf(*(listOf("duration").toTypedArray()))
//            val averageDuration = withDurations.meanOf("duration")

            val fullSpentTime = some.sum(*listOf("duration").toTypedArray()).toLong()


            val aggregated = withDurations.groupBy("name")
                .aggregate {
                    sum("duration") into "totalDuration"
                }

            // Конвертація у Map
            val categoryDurations = aggregated.rows().associate {
                it["name"] as String to it["totalDuration"] as Long
            }.map {
                DataChart(it.key, it.value.toDouble())
            }

            val categoryProportions = some.rows().associate {
                it["name"] as String to it["proportion"] as Double
            }.map {
                DataChart(it.key, it.value)
            }

            _state.update {
                it.copy(
                    fullWastedTime = fullSpentTime,
                    categoryDuration = categoryDurations,
                    averageSpentTime = categoryProportions
                )
            }


            // Виведення результату
            println(categoryProportions)

            println(some)

        }
    }
}