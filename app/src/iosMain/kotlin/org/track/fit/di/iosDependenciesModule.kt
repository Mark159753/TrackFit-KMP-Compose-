package org.track.fit.di

import org.koin.dsl.module
import org.track.fit.common.di.IosDependenciesProvider

fun iosDependenciesModule(iosDependenciesProvider: IosDependenciesProvider) = module {
    factory { iosDependenciesProvider.getGoogleAuthProvider() }
    factory<IosDependenciesProvider> { iosDependenciesProvider }
}