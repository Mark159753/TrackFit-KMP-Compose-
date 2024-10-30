package org.track.fit.ui.util.window_size

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.track.fit.ui.extensions.getActivityOrNull
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass as calcWindowSize

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
actual fun calculateWindowSizeClass(): WindowSizeClass {
    val activity = LocalContext.current.getActivityOrNull()
        ?: throw IllegalStateException("Require activity but got null")
    return calcWindowSize(activity)
}