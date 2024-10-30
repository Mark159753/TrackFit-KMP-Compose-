package org.track.fit.track.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.track.fit.track.ui.state.LocationState

@Composable
expect fun GoogleMapView(modifier: Modifier, state: LocationState)