package io.vladyslavv_ua.time_tracker.feature.projects.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProjectNameDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, onInput: (String) -> Unit, newName: String) {
    val requester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        requester.requestFocus()
    }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background).padding(16.dp)
            .onKeyEvent { keyEvent: KeyEvent ->
                if ((keyEvent.key == Key.Enter || keyEvent.key == Key.NumPadEnter) && keyEvent.type == KeyEventType.KeyDown) {
                    onConfirm()
                    true
                } else false
            }.focusRequester(requester)
            .focusable(),

        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("New project name", color = MaterialTheme.colorScheme.onBackground)
            TextField(
                newName,
                onValueChange = { onInput(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().onPreviewKeyEvent { keyEvent: KeyEvent ->
                    if ((keyEvent.key == Key.Enter || keyEvent.key == Key.NumPadEnter) && keyEvent.type == KeyEventType.KeyDown) {
                        onConfirm()
                        true
                    } else false
                }
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
                TextButton(onClick = onConfirm) {
                    Text("OK")
                }
            }
        }
    }
}