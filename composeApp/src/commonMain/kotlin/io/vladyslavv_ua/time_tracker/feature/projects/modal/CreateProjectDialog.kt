package io.vladyslavv_ua.time_tracker.feature.projects.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.vladyslavv_ua.time_tracker.feature.projects.ProjectsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProjectDialog(
    state: ProjectsState,
    onDismiss: () -> Unit,
    onInput: (String) -> Unit,
    onCreatePressed: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background).padding(16.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Create Project", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            TextField(
                state.projectName,
                onValueChange = { onInput(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().onPreviewKeyEvent { keyEvent: KeyEvent ->
                    if ((keyEvent.key == Key.Enter || keyEvent.key == Key.NumPadEnter) && keyEvent.type == KeyEventType.KeyDown) {
                        onCreatePressed()
                        true
                    }

                    else false
                }
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
                TextButton(onClick = { onCreatePressed() }, modifier = Modifier) {
                    Text("OK")
                }

            }

        }
    }
}