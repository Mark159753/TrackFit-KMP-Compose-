package org.track.fit.account.ui.state

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.track.fit.common.constants.AppLanguage
import org.track.fit.local.preferences.AppPreferences
import org.track.fit.ui.util.language.LanguageManager

@Stable
interface AccountSettingsState {

    val isDarkTheme:Flow<Boolean>

    val currentLng:Flow<AppLanguage>

    fun toggleTheme(isDark:Boolean)

    fun changeLng(lng:AppLanguage)
}

class AccountSettingsStateImpl(
    private val appPreferences: AppPreferences,
    private val languageManager: LanguageManager,
    private val scope: CoroutineScope
):AccountSettingsState{

    override val isDarkTheme: Flow<Boolean> = appPreferences.isDarkMode.filterNotNull()

    override val currentLng: Flow<AppLanguage> = languageManager.currentLng

    override fun toggleTheme(isDark: Boolean) {
        scope.launch {
            appPreferences.changeDarkMode(isDark)
        }
    }

    override fun changeLng(lng: AppLanguage) {
        scope.launch {
            languageManager.changeLng(lng)
        }
    }
}
