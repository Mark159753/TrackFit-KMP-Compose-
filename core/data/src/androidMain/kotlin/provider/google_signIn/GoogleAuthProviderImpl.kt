package provider.google_signIn

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import co.touchlab.kermit.Logger
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import org.track.fit.common.BuildConfig
import org.track.fit.common.provider.GoogleAccount
import org.track.fit.common.provider.GoogleAuthProvider

class GoogleAuthProviderImpl(
    private val credentialManager: CredentialManager
): GoogleAuthProvider {

    override suspend fun signIn(context: Context): GoogleAccount? = try {
        val credential = credentialManager.getCredential(
            context = context,
            request = getCredentialRequest()
        ).credential
        handleSignIn(credential)
    } catch (e: Exception) {
        Logger.e(tag = "GoogleAuthProvider", throwable = e, messageString = "ERROR")
        null
    }

    private fun handleSignIn(credential: Credential): GoogleAccount? = when {
        credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                GoogleAccount(
                    token = googleIdTokenCredential.idToken,
                    displayName = googleIdTokenCredential.displayName ?: "",
                    profileImageUrl = googleIdTokenCredential.profilePictureUri?.toString()
                )
            } catch (e: GoogleIdTokenParsingException) {
                null
            }
        }
        else -> null
    }

    private fun getCredentialRequest(): GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption())
        .build()

    private fun getGoogleIdOption(): GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(false)
        .setServerClientId(BuildConfig.WEB_CLIENT_ID)
        .build()
}