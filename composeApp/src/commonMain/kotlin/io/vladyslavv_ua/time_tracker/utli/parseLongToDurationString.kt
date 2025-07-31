package io.vladyslavv_ua.time_tracker.utli


fun parseToDurationString(dur: Long): String {
    val days = dur / 86400
    val hours = (dur % 86400) / 3600
    val minutes = (dur % 3600) / 60
    val seconds = dur % 60

    val formatDays = if (days == 0L) "" else "$days days "
    val formatHours = if (hours == 0L) "" else "$hours hours "
    val formatMinutes = if (minutes == 0L) "" else "$minutes minutes "
    val formatSeconds = if (seconds == 0L) "" else "$seconds seconds"

    return "$formatDays$formatHours$formatMinutes$formatSeconds"
}