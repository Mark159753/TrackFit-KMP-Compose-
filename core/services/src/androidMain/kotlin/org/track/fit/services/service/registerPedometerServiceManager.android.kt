package org.track.fit.services.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.koin.compose.koinInject

@Composable
actual fun registerPedometerServiceManager(): PedometerServiceManager {
    val manager:PedometerServiceManager = koinInject()
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(lifecycle) {
        val lifecycleManager = manager as DefaultLifecycleObserver
        lifecycle.lifecycle.addObserver(lifecycleManager)
    }
    return manager
}