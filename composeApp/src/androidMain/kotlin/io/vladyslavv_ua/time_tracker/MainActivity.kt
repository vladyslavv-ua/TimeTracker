package io.vladyslavv_ua.time_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.vladyslavv_ua.time_tracker.datasource.room.AppDB
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }

    override fun onPause() {
        super.onPause()

//        if (isFinishing) {
//
//            val appDB by inject<AppDB>()
//            appDB.close()
//            println("app database closed")
//        }
    }

}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}