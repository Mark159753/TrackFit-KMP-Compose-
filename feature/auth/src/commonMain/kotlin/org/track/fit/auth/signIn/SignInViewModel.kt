package org.track.fit.auth.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.track.fit.auth.signIn.state.SignInState
import org.track.fit.auth.signIn.state.SignInStateImpl
import org.track.fit.data.repository.user.UserRepository
import org.track.fit.domain.usecase.LoginWithGoogleUC
import org.track.fit.domain.usecase.SignInUC
import org.track.fit.ui.util.actions.UIActions
import org.track.fit.ui.util.actions.UIActionsImpl

class SignInViewModel(
    signInUC: SignInUC,
    loginWithGoogleUC: LoginWithGoogleUC
): ViewModel(),
 UIActions by UIActionsImpl(){

    val state:SignInState = SignInStateImpl(
        scope = viewModelScope,
        uiAction = this,
        signInUC = signInUC,
        loginWithGoogleUC = loginWithGoogleUC
    )
}