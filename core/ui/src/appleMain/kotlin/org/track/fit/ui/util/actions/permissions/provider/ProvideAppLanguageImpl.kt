package org.track.fit.ui.util.actions.permissions.provider

import org.track.fit.common.constants.AppLanguage
import org.track.fit.common.provider.ProvideAppLanguage
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

class ProvideAppLanguageImpl: ProvideAppLanguage {

    override fun getAppLanguage() = try {
        AppLanguage.valueOf(NSLocale.currentLocale.languageCode.uppercase())
    }catch (e:Exception){
        AppLanguage.EN
    }
}