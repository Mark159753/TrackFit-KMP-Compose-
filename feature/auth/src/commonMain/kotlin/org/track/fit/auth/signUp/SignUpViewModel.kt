package org.track.fit.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.track.fit.auth.signUp.state.SignUpState
import org.track.fit.auth.signUp.state.SignUpStateImpl
import org.track.fit.domain.usecase.LoginWithGoogleUC
import org.track.fit.domain.usecase.SignUpUC
import org.track.fit.ui.util.actions.UIActions
import org.track.fit.ui.util.actions.UIActionsImpl

class SignUpViewModel(
    signUpUC: SignUpUC,
    loginWithGoogleUC: LoginWithGoogleUC
):ViewModel(),
    UIActions by UIActionsImpl() {

    val state:SignUpState = SignUpStateImpl(
        scope = viewModelScope,
        signUpUC = signUpUC,
        uiAction = this,
        loginWithGoogleUC = loginWithGoogleUC
    )

}