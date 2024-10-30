package org.track.fit.ui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.track.fit.common.provider.ProvideAppLanguage
import org.track.fit.ui.util.actions.permissions.provider.ProvideAppLanguageImpl
import org.track.fit.ui.util.language.LanguageManager
import org.track.fit.ui.util.language.LanguageManagerImpl

actual val uiModule = module {
    singleOf(::ProvideAppLanguageImpl).bind<ProvideAppLanguage>()
    singleOf(::LanguageManagerImpl).bind<LanguageManager>()
}