package io.vladyslavv_ua.time_tracker.globalInterface

interface IViewModelMVI<T : IINtent> {
    fun onIntent(intent: T)
}