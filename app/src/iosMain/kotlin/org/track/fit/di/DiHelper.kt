package org.track.fit.di

import org.koin.core.context.startKoin
import org.track.fit.common.di.IosDependenciesProvider

fun initKoin(iosDependenciesProvider: IosDependenciesProvider){
    startKoin {
        modules(appModules() + iosDependenciesModule(iosDependenciesProvider))
    }
}