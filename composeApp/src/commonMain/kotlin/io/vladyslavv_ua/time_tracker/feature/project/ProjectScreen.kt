package io.vladyslavv_ua.time_tracker.feature.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.vladyslavv_ua.time_tracker.feature.project.component.TimeLapComponent
import io.vladyslavv_ua.time_tracker.navigation.Graph
import org.jetbrains.compose.resources.painterResource
import timetracker.composeapp.generated.resources.Res
import timetracker.composeapp.generated.resources.ic_equalizer_24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(navController: NavController, viewModel: ProjectViewModel) {
    val state by viewModel.projectState.collectAsState()


    val listState = rememberLazyListState()

    LaunchedEffect(state.projectWithTimeLaps?.timeLaps?.size) {
        if (state.lastIntent !is ProjectIntent.DeleteTimeLap) {
            listState.animateScrollToItem(0)

        }
    }

    LaunchedEffect(Unit){
        viewModel.projectSideEffect.collect { effect ->
            when(effect) {
                is ProjectSideEffect.OpenProject -> {
                    navController.navigate(Graph.ProjectStatistics(effect.projectId))
                }
            }
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = state.projectWithTimeLaps?.project?.name ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.onIntent(ProjectIntent.OpenProjectStatistics)
                    }) {
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = listState,
            ) {
                items(state.projectWithTimeLaps?.timeLaps?.reversed() ?: emptyList()) { timeLap ->
                    TimeLapComponent(timeLap, onTimeLapClick = {
                        navController.navigate(Graph.TimeLap(it.id))
                    }) {
                        viewModel.onIntent(ProjectIntent.DeleteTimeLap(timeLap.id))
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
                Text(if (state.projectWithTimeLaps?.timeLaps?.isEmpty() == true) "Start" else if (state.projectWithTimeLaps?.timeLaps?.lastOrNull()?.endedAt == null) "Stop" else "Start")
            }
        }
    }

}