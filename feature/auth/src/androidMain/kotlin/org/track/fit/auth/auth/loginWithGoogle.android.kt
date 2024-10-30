package org.track.fit.auth.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import org.track.fit.auth.states.SignInWithGoogleState
import org.track.fit.ui.extensions.getActivityOrNull

@Composable
actual inline fun loginWithGoogle(
    state: SignInWithGoogleState
):Login {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val callback:Login = remember { Login {
        val activity = context.getActivityOrNull() ?: return@Login
        scope.launch {
            state.onSignInWithGoogle(activity)
        }
    } }

    return callback
}