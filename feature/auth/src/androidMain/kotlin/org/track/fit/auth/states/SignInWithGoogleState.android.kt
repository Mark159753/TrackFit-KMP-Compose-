package org.track.fit.auth.states

import android.content.Context
import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import org.track.fit.common.actions.CommonAction
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.common.ui.strings.toUiText
import org.track.fit.domain.usecase.LoginWithGoogleUC
import org.track.fit.ui.util.actions.UIActions

@Stable
actual interface SignInWithGoogleState{

    suspend fun onSignInWithGoogle(context:Context)
}

actual class SignInWithGoogleStateImpl actual constructor(
    scope: CoroutineScope,
    private val uiActions: UIActions,
    private val loginWithGoogleUC: LoginWithGoogleUC
) : SignInWithGoogleState{

    override suspend fun onSignInWithGoogle(context: Context) {
        when (val result = loginWithGoogleUC(context)) {
            is Result.Error -> {
                if (result.error != AuthError.Cancel) {
                    uiActions.sendAction(CommonAction.ShowSnackBar(result.error.toUiText()))
                }
            }

            is Result.Success -> {
                uiActions.sendAction(result.data)
            }
        }
    }
}