package io.vladyslavv_ua.time_tracker.utli

fun parseTimeToString(days: Long, hours: Int, minutes: Int, seconds: Int): String {
    val parts = mutableListOf<String>()

    if (days > 0) parts.add("$days days")
    if (hours > 0) parts.add("$hours hours")
    if (minutes > 0) parts.add("$minutes minutes")
    if (seconds > 0) parts.add("$seconds seconds")

    return parts.joinToString(", ")
}