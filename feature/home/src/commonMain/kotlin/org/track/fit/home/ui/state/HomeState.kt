package org.track.fit.home.ui.state

import androidx.compose.runtime.Stable

@Stable
interface HomeState:PedometerState, DailyProgressState {
}

class HomeStateImpl(
        pedometerState: PedometerState,
        dailyProgressState: DailyProgressState
):HomeState,
        PedometerState by pedometerState,
        DailyProgressState by dailyProgressState

