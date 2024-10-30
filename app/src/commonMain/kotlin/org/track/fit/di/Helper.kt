package org.track.fit.di

import org.track.fit.common.di.scopeModule
import org.track.fit.data.di.firebaseModule
import org.track.fit.data.di.providersModule
import org.track.fit.data.di.repositoryModule
import org.track.fit.domain.di.useCaseModule
import org.track.fit.local.di.dataStoreModule
import org.track.fit.services.di.serviceCommonModule
import org.track.fit.services.di.servicesModule
import org.track.fit.track.di.trackModule
import org.track.fit.ui.di.uiModule

fun appModules() = listOf(
    viewmodelModule,
    repositoryModule,
    dataStoreModule,
    useCaseModule,
    providersModule,
    servicesModule,
    serviceCommonModule,
    firebaseModule,
    scopeModule,
    trackModule,
    uiModule
)