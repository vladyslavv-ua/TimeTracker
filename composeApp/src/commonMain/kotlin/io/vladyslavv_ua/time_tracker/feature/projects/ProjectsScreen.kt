package io.vladyslavv_ua.time_tracker.feature.projects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.vladyslavv_ua.time_tracker.feature.projects.modal.CreateProjectDialog
import io.vladyslavv_ua.time_tracker.feature.projects.modal.DeleteConfirmationDialog
import io.vladyslavv_ua.time_tracker.feature.projects.modal.EditProjectNameDialog
import io.vladyslavv_ua.time_tracker.navigation.Graph
import io.vladyslavv_ua.time_tracker.navigation.RootNavigation
import org.jetbrains.compose.resources.vectorResource
import timetracker.composeapp.generated.resources.Res
import timetracker.composeapp.generated.resources.ic_equalizer_24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(navController: NavController, viewModel: ProjectsViewModel) {
    val state by viewModel.projectsState.collectAsState()

    var deleteDialogShown by remember { mutableStateOf(false) }
    var deleteProjectId: Long by remember { mutableStateOf(-1) }



    if (deleteDialogShown) {
        DeleteConfirmationDialog(onDismiss = { deleteDialogShown = false }) {
            viewModel.onIntent(ProjectsIntent.DeleteProject(deleteProjectId))
            deleteDialogShown = false

        }
    }

    var editDialogShown by remember { mutableStateOf(false) }
    var editProjectId: Long by remember { mutableStateOf(-1) }

    if (editDialogShown) {
        EditProjectNameDialog(
            onDismiss = { editDialogShown = false },
            onConfirm = {
                viewModel.onIntent(ProjectsIntent.UpdateProjectName(editProjectId, state.updateProjectNameField))
                editProjectId = -1
                editDialogShown = false
            },
            onInput = {
                viewModel.onIntent(ProjectsIntent.UpdateProjectNameField(it))

            },
            state.updateProjectNameField,
        )
    }

    if (state.isModalShowed) {
        CreateProjectDialog(
            state,
            onDismiss = { viewModel.onIntent(ProjectsIntent.ToggleModalVisibility) },
            onInput = { viewModel.onIntent(ProjectsIntent.UpdateCreateProjectField(it)) }) {
            viewModel.onIntent(ProjectsIntent.CreateProject)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Projects")
                        Button(
                            onClick = { navController.navigate(Graph.FullStatistics) }
                        ) {
                            Icon(imageVector = vectorResource(Res.drawable.ic_equalizer_24), contentDescription = null)

                        }
                    }
                },
            )
        },


        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onIntent(ProjectsIntent.ToggleModalVisibility)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "New project")
            }
        }) { innerPadding ->

        if (state.projects.isEmpty()) {
            Box(Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No projects", fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
            }
        } else {

            LazyColumn(
                modifier = Modifier.padding(innerPadding).fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.projects, key = { it.id }) { project ->
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(40.dp),
                        tonalElevation = 16.dp,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxHeight()) {
                            Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable {
                                navController.navigate(Graph.Project(project.id))

                            }.padding(8.dp), contentAlignment = Alignment.CenterStart) {
                                Text(project.name)
                            }

                            IconButton(
                                onClick = {
                                    deleteDialogShown = true
                                    deleteProjectId = project.id
                                },
                                modifier = Modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max)
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete project")
                            }
                            IconButton(onClick = {
                                editProjectId = project.id
                                editDialogShown = true
                            }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Change name")
                            }
                        }

                    }
                }
            }
        }


    }
}