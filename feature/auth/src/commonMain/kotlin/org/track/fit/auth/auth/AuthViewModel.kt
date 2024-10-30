package org.track.fit.auth.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.track.fit.auth.states.SignInWithGoogleState
import org.track.fit.auth.states.SignInWithGoogleStateImpl
import org.track.fit.domain.usecase.LoginWithGoogleUC
import org.track.fit.ui.util.actions.UIActions
import org.track.fit.ui.util.actions.UIActionsImpl

class AuthViewModel(
    loginWithGoogleUC: LoginWithGoogleUC
): ViewModel(),
    UIActions by UIActionsImpl(){

    val state: SignInWithGoogleState = SignInWithGoogleStateImpl(
        scope = viewModelScope,
        uiActions = this,
        loginWithGoogleUC = loginWithGoogleUC
    )

}