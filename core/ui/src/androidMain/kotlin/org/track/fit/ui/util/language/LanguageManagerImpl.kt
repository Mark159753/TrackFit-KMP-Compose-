package org.track.fit.ui.util.language

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.text.intl.Locale
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.track.fit.common.constants.AppLanguage
import org.track.fit.local.preferences.AppPreferences

class LanguageManagerImpl(
    private val appSettings: AppPreferences,
    private val appContext: Context
):LanguageManager {

    override val currentLng: Flow<AppLanguage> = appSettings.currentLocale

    override suspend fun changeLng(lng: AppLanguage) {
        val tage = Locale(lng.name.lowercase()).toLanguageTag()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            appContext.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(tage)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(tage)
            )
        }
        appSettings.setLocale(lng)
    }

    override suspend fun saveInitLng(systemLng: AppLanguage?) {
        val savedLng = currentLng.firstOrNull()
        if (savedLng == null){
            appSettings.setLocale(systemLng ?: AppLanguage.EN)
        }
    }
}

suspend fun LanguageManager.saveInitLng(context: Context){
    val savedLng = currentLng.firstOrNull()
    if (savedLng == null){
        val systemLng = context.getCurrentLng()
        try {
             saveInitLng(AppLanguage.valueOf(systemLng.uppercase()))
        }catch (e:Exception){
            saveInitLng(null)
        }
    }
}

fun Context.getCurrentLng():String{
    return resources.configuration.locales.get(0).language
}

fun String.toAppLanguageOrNull() = try {
    AppLanguage.valueOf(this.uppercase())
}catch (e:Exception){
    null
}