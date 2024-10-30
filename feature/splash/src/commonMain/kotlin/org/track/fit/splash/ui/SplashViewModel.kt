package org.track.fit.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.track.fit.common.actions.NavAction
import org.track.fit.data.repository.user.UserRepository
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.ui.util.actions.UIActions
import org.track.fit.ui.util.actions.UIActionsImpl

class SplashViewModel(
    private val userRepository: UserRepository,
    private val preferencesRepository: PersonalPreferencesRepository,
):ViewModel(),
    UIActions by UIActionsImpl(){

    init {
        routeTo()
    }

    private fun routeTo(){
        viewModelScope.launch {
            val user = userRepository.user.firstOrNull()
            val action = when{
                user == null -> NavAction.NavToAuth
                !preferencesRepository.isFieldExist(PersonalPreferences.StepsGoal) -> NavAction.NavToRegistrationSteps
                else -> NavAction.NavToHome
            }
            sendAction(action)
        }
    }
}