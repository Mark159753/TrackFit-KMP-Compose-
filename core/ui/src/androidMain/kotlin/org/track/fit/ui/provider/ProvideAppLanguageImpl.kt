package org.track.fit.ui.provider

import androidx.appcompat.app.AppCompatDelegate
import org.track.fit.common.constants.AppLanguage
import org.track.fit.common.provider.ProvideAppLanguage

class ProvideAppLanguageImpl: ProvideAppLanguage {

    override fun getAppLanguage() = try {
        AppLanguage.valueOf(AppCompatDelegate.getApplicationLocales()[0]!!.language.uppercase())
    }catch (e:Exception){
        AppLanguage.EN
    }
}