package org.track.fit.track.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import org.track.fit.track.ui.state.LocationState
import org.track.fit.ui.theme.isDarkTheme

@Composable
actual fun GoogleMapView(modifier: Modifier, state: LocationState) {

    val userPosition by state.currentLocation.collectAsStateWithLifecycle()
    val trackPosition by state.trackUserPosition.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng( userPosition?.lat ?:49.843181, userPosition?.lng ?: 24.026281),
            12f)
    }

    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(trackPosition, lifecycle.lifecycle) {
        state.currentLocation
            .flowWithLifecycle(lifecycle.lifecycle)
            .filterNotNull()
            .collectLatest { pos ->
                if (trackPosition){
                    cameraPositionState.animate(
                        update = CameraUpdateFactory
                            .newCameraPosition(
                                CameraPosition.fromLatLngZoom(
                                    LatLng(pos.lat, pos.lng),
                                    16f
                                )
                            )
                    )
                }
            }
    }

    LaunchedEffect(cameraPositionState.cameraMoveStartedReason) {
        when(cameraPositionState.cameraMoveStartedReason){
            CameraMoveStartedReason.GESTURE -> {
                state.onToggleTrackUser(false)
            }else -> {}
        }
    }

    val routes by state.tracks.collectAsStateWithLifecycle()

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomGesturesEnabled = true
            )
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings,
        mapColorScheme = if (MaterialTheme.isDarkTheme) ComposeMapColorScheme.LIGHT else ComposeMapColorScheme.LIGHT
    ){

        routes.forEach { track ->
            Polyline(
                points = track.route.map { LatLng(it.lat, it.lng) },
                color = Color.Red
            )
        }
    }
}