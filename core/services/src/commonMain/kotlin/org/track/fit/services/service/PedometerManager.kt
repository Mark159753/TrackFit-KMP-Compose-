package org.track.fit.services.service

import kotlinx.coroutines.flow.StateFlow
import org.track.fit.common.ui.strings.UIText
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.home_step_counter_not_available
import trackfit.core.common.generated.resources.home_step_counter_permission_required

interface PedometerManager {

    val state:StateFlow<PedometerState>

    suspend fun startObserving()
}

sealed interface PedometerState{
    data object Stop:PedometerState
    data object Running:PedometerState
    data class Error(val error:PedometerError):PedometerState
}

sealed interface PedometerError{

    val msg:UIText

    data class NotSupported(
        override val msg:UIText = UIText.ResString(Res.string.home_step_counter_not_available)
    ):PedometerError
    data class PermissionDenied(
        override val msg:UIText = UIText.ResString(Res.string.home_step_counter_permission_required)
    ):PedometerError
}