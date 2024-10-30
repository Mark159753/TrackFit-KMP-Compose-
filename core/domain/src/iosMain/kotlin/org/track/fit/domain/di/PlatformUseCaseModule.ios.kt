package org.track.fit.domain.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.track.fit.domain.usecase.LogOutUC
import org.track.fit.domain.usecase.LoginWithGoogleUC

internal actual val platformUseCaseModule: Module = module {
    factoryOf(::LogOutUC)
    factoryOf(::LoginWithGoogleUC)

}