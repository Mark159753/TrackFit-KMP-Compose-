package org.track.fit.domain.usecase

import org.track.fit.common.actions.NavAction
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.data.repository.user.UserRepository

actual class LogOutUC(
    private val userRepository:UserRepository
) {

    actual suspend operator fun invoke(): Result<NavAction, AuthError> {
        val response = userRepository.signOut()
        return if (response == null){
            Result.Success(NavAction.NavToAuth)
        }else{
            Result.Error(response)
        }
    }

}