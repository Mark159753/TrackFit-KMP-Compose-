package org.track.fit.track.ui.state

import androidx.compose.runtime.Stable

@Stable
interface TrackState:PedometerState, LocationState {
}

class TrackStateImpl(
    pedometerState: PedometerState,
    locationState: LocationState
):TrackState,
    PedometerState by pedometerState,
    LocationState by locationState