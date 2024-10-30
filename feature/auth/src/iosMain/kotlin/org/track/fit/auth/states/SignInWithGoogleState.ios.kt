package org.track.fit.auth.states

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.track.fit.common.actions.CommonAction
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.common.ui.strings.toUiText
import org.track.fit.domain.usecase.LoginWithGoogleUC
import org.track.fit.ui.util.actions.UIActions

@Stable
actual interface SignInWithGoogleState{

    fun onSignInWithGoogle()
}

actual class SignInWithGoogleStateImpl actual constructor(
    private val scope: CoroutineScope,
    private val uiActions: UIActions,
    private val loginWithGoogleUC: LoginWithGoogleUC
) : SignInWithGoogleState{

    override fun onSignInWithGoogle() {
        scope.launch {
            when(val result = loginWithGoogleUC()){
                is Result.Error -> {
                    if (result.error != AuthError.Cancel){
                        uiActions.sendAction(CommonAction.ShowSnackBar(result.error.toUiText()))
                    }
                }
                is Result.Success -> {
                    uiActions.sendAction(result.data)
                }
            }
        }
    }
}