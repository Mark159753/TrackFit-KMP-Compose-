package org.track.fit.account.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.track.fit.account.ui.state.AccountSettingsStateImpl
import org.track.fit.account.ui.state.AccountStateImpl
import org.track.fit.domain.usecase.LogOutUC
import org.track.fit.local.preferences.AppPreferences
import org.track.fit.services.service.PedometerServiceManager
import org.track.fit.ui.util.actions.UIActions
import org.track.fit.ui.util.actions.UIActionsImpl
import org.track.fit.ui.util.language.LanguageManager

class AccountViewModel(
    logOutUC: LogOutUC,
    appPreferences: AppPreferences,
    languageManager: LanguageManager,
    pedometerManager: PedometerServiceManager
):ViewModel(),
    UIActions by UIActionsImpl() {

    val state = AccountStateImpl(
        logOutUC = logOutUC,
        scope = viewModelScope,
        uiActions = this,
        pedometerManager = pedometerManager,
        accountSettingsState = AccountSettingsStateImpl(
            appPreferences = appPreferences,
            scope = viewModelScope,
            languageManager = languageManager
        )
    )
}