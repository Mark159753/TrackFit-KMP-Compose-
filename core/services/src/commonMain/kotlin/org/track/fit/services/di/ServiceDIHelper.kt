package org.track.fit.services.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.track.fit.services.location.tracker.LocationTracker
import org.track.fit.services.service.PedometerManager

class ServiceDIHelper:KoinComponent {

    private val pedometerManager: PedometerManager by inject()
    private val locationTracker: LocationTracker by inject()

    fun providePedometerManager() = pedometerManager

    fun provideLocationTracker() = locationTracker

}