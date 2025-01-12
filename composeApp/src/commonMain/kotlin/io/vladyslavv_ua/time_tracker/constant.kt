package io.vladyslavv_ua.time_tracker

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char

val dateFormat = LocalDateTime.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
    char(' ')
    hour()
    char(':')
    minute()
    char(':')
    second()
}