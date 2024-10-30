package org.track.fit.services.service

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject

@Composable
actual fun registerPedometerServiceManager(): PedometerServiceManager {
    val manager:PedometerServiceManager = koinInject()
    return manager
}