package org.track.fit.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import org.track.fit.common.constants.AppLanguage
import org.track.fit.common.date.toLocalDateTime
import org.track.fit.common.date.toMilliseconds
import org.track.fit.common.provider.ProvideAppLanguage

class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
    private val appLanguageProvider: ProvideAppLanguage
) : AppPreferences {

    private val questStartTimeKey = longPreferencesKey("$PREFS_TAG_KEY$START_TIME")
    private val darkModeKey = booleanPreferencesKey("$PREFS_TAG_KEY$IS_DARK_MODE")
    private val appLocaleKey = stringPreferencesKey("$PREFS_TAG_KEY$APP_LOCALE")

    override val questStartTime = dataStore.data.map { preferences ->
        preferences[questStartTimeKey]?.toLocalDateTime()
    }

    override val isDarkMode: Flow<Boolean?> = dataStore.data.map { preferences ->
        preferences[darkModeKey]
    }

    override val currentLocale: Flow<AppLanguage> = dataStore.data.map { preferences ->
        preferences[appLocaleKey]?.let { lng ->
            try {
                AppLanguage.valueOf(lng)
            }catch (e:Exception){
                appLanguageProvider.getAppLanguage()
            }
        } ?: appLanguageProvider.getAppLanguage()
    }

    override suspend fun setStartTime(time:LocalDateTime){
        dataStore.edit { preferences ->
            preferences[questStartTimeKey] = time.toMilliseconds()
        }
    }

    override suspend fun setLocale(lng: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[appLocaleKey] = lng.name
        }
    }

    override suspend fun changeDarkMode(isEnabled: Boolean?) {
        dataStore.edit { preferences ->
            if (isEnabled == null){
                preferences.remove(darkModeKey)
            }else{
                preferences[darkModeKey] = isEnabled
            }
        }
    }


}

private const val PREFS_TAG_KEY = "AppPreferences"
private const val START_TIME = "AppPreferences"