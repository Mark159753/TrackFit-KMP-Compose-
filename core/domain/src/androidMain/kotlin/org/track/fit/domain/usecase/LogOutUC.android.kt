package org.track.fit.domain.usecase

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import org.track.fit.common.actions.NavAction
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.data.repository.user.UserRepository

actual class LogOutUC(
    private val userRepository: UserRepository,
    private val credentialManager: CredentialManager,
) {
    actual suspend operator fun invoke(): Result<NavAction, AuthError> {
        val response = userRepository.signOut()
        return if (response == null){
            try {
                credentialManager.clearCredentialState(ClearCredentialStateRequest())
                Result.Success(NavAction.NavToAuth)
            }catch (e:Exception){
                Result.Error(AuthError.UnknownError)
            }
        }else{
            Result.Error(response)
        }
    }

}