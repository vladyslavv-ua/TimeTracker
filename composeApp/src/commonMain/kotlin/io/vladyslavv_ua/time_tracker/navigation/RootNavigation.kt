package io.vladyslavv_ua.time_tracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.vladyslavv_ua.time_tracker.feature.fullStatistics.FullStatisticsScreen
import io.vladyslavv_ua.time_tracker.feature.fullStatistics.FullStatisticsScreenVM
import io.vladyslavv_ua.time_tracker.feature.project.ProjectScreen
import io.vladyslavv_ua.time_tracker.feature.project.ProjectViewModel
import org.koin.compose.koinInject
import io.vladyslavv_ua.time_tracker.feature.projects.ProjectsScreen
import io.vladyslavv_ua.time_tracker.feature.projects.ProjectsViewModel
import io.vladyslavv_ua.time_tracker.feature.timeLapInfo.TimeLapInfoScreen
import io.vladyslavv_ua.time_tracker.feature.timeLapInfo.TimeLapInfoViewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication


@Composable
fun RootNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = Graph.Projects) {
        composable<Graph.Projects> {
            val vm: ProjectsViewModel = koinInject()
            ProjectsScreen(navController, vm)
        }
        composable<Graph.Project> { entry ->
            val projectId: Graph.Project = entry.toRoute()
            val vm: ProjectViewModel = koinInject {
                parametersOf(projectId.id)
            }
            ProjectScreen(navController, vm)
        }
        composable<Graph.TimeLap> { entry ->
            val timeLapId: Graph.TimeLap = entry.toRoute()
            val vm: TimeLapInfoViewModel = koinInject {
                parametersOf(timeLapId.id)
            }
            TimeLapInfoScreen(navController, vm)
        }

        composable<Graph.FullStatistics> {

            val vm: FullStatisticsScreenVM = koinInject()
            FullStatisticsScreen(navController, vm)
        }

    }
}