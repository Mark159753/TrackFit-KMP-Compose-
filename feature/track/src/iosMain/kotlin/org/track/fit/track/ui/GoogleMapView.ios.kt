package org.track.fit.track.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.compose.koinInject
import org.track.fit.common.di.IosDependenciesProvider
import org.track.fit.track.ui.state.LocationState

@Composable
actual fun GoogleMapView(modifier: Modifier, state: LocationState) {
    SwiftGoogleMap(
        modifier = modifier
    )
}

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun SwiftGoogleMap(
    modifier: Modifier = Modifier,
    dependenciesProvider: IosDependenciesProvider = koinInject()
){
    val mapFactory = remember(dependenciesProvider) {
        { dependenciesProvider.getGoogleMapView() }
    }

    UIKitViewController(
        factory = mapFactory,
        modifier = modifier,
    )
}