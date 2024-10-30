package org.track.fit.common.provider

actual interface GoogleAuthProvider{

    suspend fun signIn(): GoogleAccount?
}