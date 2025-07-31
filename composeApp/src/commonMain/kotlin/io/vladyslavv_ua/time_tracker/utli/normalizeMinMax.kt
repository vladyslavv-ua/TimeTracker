package io.vladyslavv_ua.time_tracker.utli

import kotlinx.datetime.LocalDate

fun <K> normalizeMinMax(data: Map<K, Long>): Map<K, Float> {
    if (data.isEmpty()) return emptyMap()

    val values = data.values
    val min = values.minOrNull() ?: 0L
    val max = values.maxOrNull() ?: 0L

    // If min equals max, all values are the same, so return 0.5 for all
    if (min == max) {
        return data.mapValues { 0.5f }
    }

    val range = (max - min).toFloat()
    return data.mapValues { (_, value) ->
        (value - min).toFloat() / range
    }
}