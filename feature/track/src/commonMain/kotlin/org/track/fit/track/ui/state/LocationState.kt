package org.track.fit.track.ui.state

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.track.fit.data.models.Location
import org.track.fit.data.models.RouteModel
import org.track.fit.data.repository.location.LocationRepository
import org.track.fit.services.location.LocationProvider

@Stable
interface LocationState {

    val currentLocation:StateFlow<Location?>

    val isMyLocationEnabled:StateFlow<Boolean>

    val tracks:StateFlow<List<RouteModel>>

    val trackUserPosition:StateFlow<Boolean>

    fun onToggleTrackUser(track:Boolean)

}

class LocationStateImpl(
    locationRepository: LocationRepository,
    locationProvider: LocationProvider,
    scope: CoroutineScope
):LocationState{

    override val currentLocation: StateFlow<Location?> = locationProvider
        .locationFlow
        .stateIn(
            scope = scope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    override val isMyLocationEnabled: StateFlow<Boolean> = currentLocation
        .map { it != null }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            initialValue = false,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private val _trackUserPosition = MutableStateFlow(true)
    override val trackUserPosition: StateFlow<Boolean>
        get() = _trackUserPosition


    override val tracks: StateFlow<List<RouteModel>> = locationRepository
        .todayRoutes()
        .stateIn(
            scope = scope,
            initialValue = emptyList(),
            started = SharingStarted.Eagerly
        )

    override fun onToggleTrackUser(track:Boolean) {
        _trackUserPosition.value = track
    }
}