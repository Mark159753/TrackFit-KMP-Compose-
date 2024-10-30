package org.track.fit.track.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.track.fit.data.repository.location.LocationRepository
import org.track.fit.data.repository.statistics.StatisticsRepository
import org.track.fit.services.location.LocationProvider
import org.track.fit.track.ui.state.LocationStateImpl
import org.track.fit.track.ui.state.PedometerStateImpl
import org.track.fit.track.ui.state.TrackStateImpl

class TrackViewModel(
    statisticsRepository: StatisticsRepository,
    locationRepository: LocationRepository,
    locationProvider: LocationProvider,
):ViewModel() {

    val state = TrackStateImpl(
        pedometerState = PedometerStateImpl(
            statisticsRepository, viewModelScope
        ),
        locationState = LocationStateImpl(
            locationRepository = locationRepository,
            locationProvider = locationProvider,
            scope = viewModelScope
        )
    )
}