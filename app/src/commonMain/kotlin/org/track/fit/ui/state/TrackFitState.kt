package org.track.fit.ui.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import org.track.fit.common.constants.AppLanguage
import org.track.fit.local.preferences.AppPreferences

interface TrackFitState {

    val isDarkTheme:Flow<Boolean>

    val appLanguage:StateFlow<AppLanguage>
}

class TrackFitStateImpl(
    appPreferences: AppPreferences,
    scope: CoroutineScope
):TrackFitState{

    override val isDarkTheme: Flow<Boolean> = appPreferences.isDarkMode.filterNotNull()

    override val appLanguage: StateFlow<AppLanguage> = appPreferences.currentLocale
        .stateIn(
            scope = scope,
            initialValue = AppLanguage.EN,
            started = SharingStarted.WhileSubscribed(5_000)
        )
}