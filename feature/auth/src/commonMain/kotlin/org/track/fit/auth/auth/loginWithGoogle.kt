package org.track.fit.auth.auth

import androidx.compose.runtime.Composable
import org.track.fit.auth.states.SignInWithGoogleState

@Composable
expect inline fun loginWithGoogle(state:SignInWithGoogleState):Login

fun interface Login{
    fun login()
}