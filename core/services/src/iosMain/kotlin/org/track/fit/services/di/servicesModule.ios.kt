package org.track.fit.services.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.track.fit.services.location.LocationProvider
import org.track.fit.services.location.LocationProviderImpl
import org.track.fit.services.service.PedometerServiceManager
import org.track.fit.services.service.PedometerServiceManagerImpl
import org.track.fit.services.service.StepCounter
import org.track.fit.services.service.StepCounterImpl

actual val servicesModule: Module = module {
    factoryOf(::StepCounterImpl).bind<StepCounter>()
    factoryOf(::LocationProviderImpl).bind<LocationProvider>()
    singleOf(::PedometerServiceManagerImpl).bind<PedometerServiceManager>()
}
