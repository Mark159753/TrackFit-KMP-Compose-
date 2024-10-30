package org.track.fit.services.service

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.StateFlow

@Stable
interface PedometerServiceManager {

    val state:StateFlow<PedometerState>

    fun start()

    fun stop()
}