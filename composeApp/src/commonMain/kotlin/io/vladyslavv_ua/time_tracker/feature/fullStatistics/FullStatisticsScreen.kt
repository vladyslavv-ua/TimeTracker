package io.vladyslavv_ua.time_tracker.feature.fullStatistics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.vladyslavv_ua.time_tracker.utli.parseTimeToString
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun FullStatisticsScreen(navController: NavController, vm: FullStatisticsScreenVM) {
    val state by vm.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    ) { innerPadding ->


        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Total spent time", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(Modifier.height(16.dp))
                PieChart(
                    state.categoryDuration.map { it.value.toFloat() },
                    label = {
                        Column {
                            Text(state.categoryDuration[it].label)
                            val duration = state.categoryDuration[it].value.toLong().toDuration(DurationUnit.SECONDS)
                            Text(
                                text =
                                    duration.toComponents { days, hours, minutes, seconds, _ ->
                                        val parts = mutableListOf<String>()

                                        if (days > 0) parts.add("$days days")
                                        if (hours > 0) parts.add("$hours hours")
                                        if (minutes > 0) parts.add("$minutes minutes")
                                        if (seconds > 0) parts.add("$seconds seconds")

                                        parts.joinToString(", ")
                                    }
                            )
                        }
                    },

                    holeSize = 0.75F,
                    holeContent = {
                        val duration = state.fullWastedTime.toDuration(DurationUnit.SECONDS)
                        Box(Modifier.fillMaxSize(.75f).align(Alignment.Center), contentAlignment = Alignment.Center) {
                            Text(
                                text =
                                    duration.toComponents { days, hours, minutes, seconds, _ ->
                                        parseTimeToString(days, hours, minutes, seconds)
                                    },
                                softWrap = true,
                                textAlign = TextAlign.Center
                            )

                        }
                    }

                )

                Spacer(Modifier.height(16.dp))
            }

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Average spent time", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(Modifier.height(16.dp))

                PieChart(
                    state.averageSpentTime.map { it.value.toFloat() },
                    label = {
                        Column {
                            Text(state.averageSpentTime[it].label)
                            val duration = state.averageSpentTime[it].value.toLong().toDuration(DurationUnit.SECONDS)
                            Text(
                                text =
                                    duration.toComponents { days, hours, minutes, seconds, _ ->
                                        val parts = mutableListOf<String>()

                                        if (days > 0) parts.add("$days days")
                                        if (hours > 0) parts.add("$hours hours")
                                        if (minutes > 0) parts.add("$minutes minutes")
                                        if (seconds > 0) parts.add("$seconds seconds")

                                        parts.joinToString(", ")
                                    }
                            )
                        }
                    },

                    holeSize = 0.75F,


                )

                Spacer(Modifier.height(16.dp))


            }


        }

    }

}



