package org.track.fit.auth.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.track.fit.auth.states.SignInWithGoogleState

@Composable
actual inline fun loginWithGoogle(
    state: SignInWithGoogleState
):Login {
    val callback:Login = remember { Login {
            state.onSignInWithGoogle()
    } }

    return callback
}