package org.track.fit.auth.states

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import org.track.fit.domain.usecase.LoginWithGoogleUC
import org.track.fit.ui.util.actions.UIActions

@Stable
expect interface SignInWithGoogleState

expect class SignInWithGoogleStateImpl(
    scope:CoroutineScope,
    uiActions: UIActions,
    loginWithGoogleUC: LoginWithGoogleUC
):SignInWithGoogleState
