package org.track.fit.track.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.track.fit.track.ui.state.LocationState
import org.track.fit.track.ui.state.LocationStateImpl

actual val trackModule = module {
    factoryOf(::LocationStateImpl).bind<LocationState>()
}