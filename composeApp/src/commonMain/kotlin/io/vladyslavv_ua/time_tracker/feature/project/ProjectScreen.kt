package io.vladyslavv_ua.time_tracker.feature.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.vladyslavv_ua.time_tracker.dateFormat
import io.vladyslavv_ua.time_tracker.navigation.Graph
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import timetracker.composeapp.generated.resources.Res
import timetracker.composeapp.generated.resources.ic_equalizer_24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(navController: NavController, viewModel: ProjectViewModel) {
    val state by viewModel.projectState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = state.projectWithTimeLaps?.project?.name ?: "")
            },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(painterResource(Res.drawable.ic_equalizer_24), contentDescription = "Statistics")
                    }
                })
        }
    ) { innerPadding ->

        
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.projectWithTimeLaps?.timeLaps ?: emptyList()) { timeLap ->
                    Surface(tonalElevation = 16.dp, shape = RoundedCornerShape(16.dp)) {

                        Column(modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 56.dp).clickable(enabled = timeLap.endedAt != null) {
                            navController.navigate(Graph.TimeLap(timeLap.id))
                        }, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                timeLap.startedAt.format(dateFormat) + " - " + if (timeLap.endedAt == null) "now" else timeLap.endedAt.format(
                                    dateFormat
                                ), textAlign = TextAlign.Center
                            )
                            if (timeLap.endedAt != null) {
                                Text(
                                    (timeLap.endedAt.toInstant(TimeZone.currentSystemDefault()) - timeLap.startedAt.toInstant(
                                        TimeZone.currentSystemDefault()
                                    )).toComponents { days, hours, minutes, seconds, _ ->
                                        val parts = mutableListOf<String>()

                                        if (days > 0) parts.add("$days days")
                                        if (hours > 0) parts.add("$hours hours")
                                        if (minutes > 0) parts.add("$minutes minutes")
                                        if (seconds > 0) parts.add("$seconds seconds")

                                        parts.joinToString(", ")
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Button(onClick = {
                if (state.projectWithTimeLaps?.timeLaps?.isEmpty() == true) {
                    viewModel.onIntent(ProjectIntent.NewLoop)
                } else if (state.projectWithTimeLaps?.timeLaps?.lastOrNull()?.endedAt == null) {
                    viewModel.onIntent(ProjectIntent.CompleteLoop)
                } else {
                    viewModel.onIntent(ProjectIntent.NewLoop)
                }
            }) {
                Text(if (state.projectWithTimeLaps?.timeLaps?.isEmpty() == true) "start" else if (state.projectWithTimeLaps?.timeLaps?.lastOrNull()?.endedAt == null) "stop" else "start")
            }
        }
    }

}