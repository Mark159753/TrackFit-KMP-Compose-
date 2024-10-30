package org.track.fit.data.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.track.fit.data.repository.location.LocationRepository
import org.track.fit.data.repository.location.LocationRepositoryImpl
import org.track.fit.data.repository.user.UserRepository
import org.track.fit.data.repository.user.UserRepositoryImpl
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.data.repository.preferences.PersonalPreferencesRepositoryImpl
import org.track.fit.data.repository.statistics.StatisticsRepository
import org.track.fit.data.repository.statistics.StatisticsRepositoryImpl

val repositoryModule = module {
    factoryOf(::UserRepositoryImpl).bind<UserRepository>()
    factoryOf(::PersonalPreferencesRepositoryImpl).bind<PersonalPreferencesRepository>()
    singleOf(::StatisticsRepositoryImpl).bind<StatisticsRepository>()
    factoryOf(::LocationRepositoryImpl).bind<LocationRepository>()
}