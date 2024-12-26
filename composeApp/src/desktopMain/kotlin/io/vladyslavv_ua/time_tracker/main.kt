package io.vladyslavv_ua.time_tracker

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.room.RoomRawQuery
import io.vladyslavv_ua.time_tracker.datasource.room.AppDB
import io.vladyslavv_ua.time_tracker.di.platformSpecificModule
import io.vladyslavv_ua.time_tracker.di.sharedSingleton
import io.vladyslavv_ua.time_tracker.di.sharedVM
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import java.awt.Dimension

fun main() = application {

    startKoin {
        modules(platformSpecificModule + sharedSingleton + sharedVM)
    }

    val coroutineScope = rememberCoroutineScope()
    Window(
        onCloseRequest = {
            val roomDB: AppDB by inject(AppDB::class.java)
            roomDB.close()

            exitApplication()

        },
        title = "Time Tracker",

        ) {
        window.minimumSize = Dimension(360, 600)
        App()
    }
}