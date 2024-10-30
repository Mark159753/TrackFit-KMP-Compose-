package org.track.fit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import org.track.fit.common.constants.AppLanguage
import org.track.fit.local.preferences.AppPreferences
import org.track.fit.ui.state.TrackFitState
import org.track.fit.ui.state.TrackFitStateImpl
import org.track.fit.ui.util.language.LanguageManager

class MainViewModel(
    appPreferences: AppPreferences,
    private val languageManager: LanguageManager
):ViewModel() {

    val state: TrackFitState = TrackFitStateImpl(
        appPreferences = appPreferences,
        scope = viewModelScope
    )

    fun saveInitLng(systemLng: AppLanguage?){
        Logger.i("Language -> $systemLng")
        viewModelScope.launch {
            languageManager.saveInitLng(systemLng)
        }
    }
}