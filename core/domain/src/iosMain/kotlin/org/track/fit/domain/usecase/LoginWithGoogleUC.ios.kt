package org.track.fit.domain.usecase

import org.track.fit.common.actions.NavAction
import org.track.fit.common.provider.GoogleAuthProvider
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.data.repository.user.UserRepository
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository

actual class LoginWithGoogleUC(
    private val userRepository: UserRepository,
    private val preferencesRepository: PersonalPreferencesRepository,
    private val googleAuthProvider: GoogleAuthProvider
) {

    suspend operator fun invoke(): Result<NavAction, AuthError> {
        val googleAccount = googleAuthProvider.signIn()
        val response = userRepository.loginWithGoogle(googleAccount)
        return when(response){
            is Result.Error -> response
            is Result.Success -> {
                val action = if (preferencesRepository.isFieldExist(PersonalPreferences.StepsGoal)) {
                    NavAction.NavToHome
                }else {
                    NavAction.NavToRegistrationSteps
                }
                Result.Success(action)
            }
        }
    }
}