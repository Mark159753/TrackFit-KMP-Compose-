package org.track.fit.local.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.track.fit.local.preferences.AppPreferences
import org.track.fit.local.preferences.AppPreferencesImpl

expect fun provideDataStoreModule(): Module

val dataStoreModule = module {
    includes(provideDataStoreModule())

    singleOf(::AppPreferencesImpl).bind<AppPreferences>()
}