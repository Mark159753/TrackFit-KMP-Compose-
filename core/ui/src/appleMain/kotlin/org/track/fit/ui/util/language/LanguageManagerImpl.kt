package org.track.fit.ui.util.language

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.track.fit.common.constants.AppLanguage
import org.track.fit.local.preferences.AppPreferences
import platform.Foundation.NSUserDefaults

class LanguageManagerImpl(
    private val appSettings: AppPreferences,
):LanguageManager {

    override val currentLng: Flow<AppLanguage> = appSettings.currentLocale

    override suspend fun changeLng(lng: AppLanguage) {
        NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lng.name.lowercase()), "AppleLanguages")
        appSettings.setLocale(lng)
    }

    override suspend fun saveInitLng(systemLng: AppLanguage?) {
        val savedLng = currentLng.firstOrNull()
        if (savedLng == null){
            appSettings.setLocale(systemLng ?: AppLanguage.EN)
        }
    }
}