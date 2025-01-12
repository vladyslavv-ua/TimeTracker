package io.vladyslavv_ua.time_tracker.feature.timeLapInfo

import io.vladyslavv_ua.time_tracker.globalInterface.IINtent

data class TimeLapInfoState(
    val text: String="",
    val updated:Boolean=false,
)

sealed class TimeLapInfoIntent : IINtent {
    data class UpdateText(val text: String) : TimeLapInfoIntent()
    data object SaveChanges : TimeLapInfoIntent()
}