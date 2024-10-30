package org.track.fit.services.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.track.fit.services.location.tracker.LocationTracker
import org.track.fit.services.location.tracker.LocationTrackerImpl
import org.track.fit.services.service.PedometerManager
import org.track.fit.services.service.PedometerManagerImpl
import org.track.fit.services.service.pedometer.Pedometer
import org.track.fit.services.service.pedometer.PedometerImpl

expect val servicesModule:Module

val serviceCommonModule = module {
    factoryOf(::PedometerImpl).bind<Pedometer>()
    factoryOf(::PedometerManagerImpl).bind<PedometerManager>()
    factoryOf(::LocationTrackerImpl).bind<LocationTracker>()
}