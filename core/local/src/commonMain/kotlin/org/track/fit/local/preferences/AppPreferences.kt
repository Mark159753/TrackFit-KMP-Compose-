package org.track.fit.local.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import org.track.fit.common.constants.AppLanguage

interface AppPreferences {

    val questStartTime:Flow<LocalDateTime?>

    val isDarkMode: Flow<Boolean?>

    val currentLocale: Flow<AppLanguage>

    suspend fun setStartTime(time:LocalDateTime)

    suspend fun setLocale(lng: AppLanguage)

    suspend fun changeDarkMode(isEnabled: Boolean?)
}

const val APP_PREFERENCES = "dice.preferences_pb"
internal const val IS_DARK_MODE = "prefsBoolean"
internal const val APP_LOCALE = "app_locale"