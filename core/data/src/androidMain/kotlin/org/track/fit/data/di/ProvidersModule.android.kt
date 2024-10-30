package org.track.fit.data.di

import androidx.credentials.CredentialManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.track.fit.common.provider.GoogleAuthProvider
import provider.google_signIn.GoogleAuthProviderImpl

actual val providersModule: Module = module {
    single { CredentialManager.create(androidApplication()) }
    factoryOf(::GoogleAuthProviderImpl).bind<GoogleAuthProvider>()
}