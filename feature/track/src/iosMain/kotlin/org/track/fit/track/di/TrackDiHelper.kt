package org.track.fit.track.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.track.fit.track.ui.state.LocationState

class TrackDiHelper:KoinComponent {

    private val locationState:LocationState by inject()

    fun provideLocationState():LocationState = locationState

}