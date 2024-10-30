package org.track.fit.registration.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.registration.ui.state.RegistrationState
import org.track.fit.registration.ui.state.RegistrationStateImpl
import org.track.fit.ui.util.actions.UIActions
import org.track.fit.ui.util.actions.UIActionsImpl

class RegistrationViewModel(
    savedStateHandle: SavedStateHandle,
    personalUserPreferencesRepository: PersonalPreferencesRepository
):ViewModel(),
    UIActions by UIActionsImpl(){

    val state: RegistrationState = RegistrationStateImpl(
        savedStateHandle = savedStateHandle,
        scope = viewModelScope,
        uiActions = this,
        personalUserPreferencesRepository = personalUserPreferencesRepository
    )
}