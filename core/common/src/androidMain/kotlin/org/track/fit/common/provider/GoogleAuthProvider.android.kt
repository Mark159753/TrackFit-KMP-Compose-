package org.track.fit.common.provider

import android.content.Context

actual interface GoogleAuthProvider{

    suspend fun signIn(context:Context): GoogleAccount?
}