package io.vladyslavv_ua.time_tracker.feature.project.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.vladyslavv_ua.time_tracker.dateFormat
import io.vladyslavv_ua.time_tracker.entity.TimeLap
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant

@Composable
fun TimeLapComponent(timeLap: TimeLap, onTimeLapClick: (TimeLap) -> Unit, onTimeLapDelete: (TimeLap) -> Unit) {
    Surface(tonalElevation = 16.dp, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                Modifier
                    .weight(1f).fillMaxHeight()
                    .clickable(enabled = timeLap.endedAt != null) {
                        onTimeLapClick(timeLap)
                    }, horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
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

            IconButton(
                onClick = { onTimeLapDelete(timeLap) },
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete time lap"
                )
            }
        }
    }
}