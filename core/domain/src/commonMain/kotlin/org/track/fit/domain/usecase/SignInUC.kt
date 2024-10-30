package org.track.fit.domain.usecase

import org.track.fit.common.actions.NavAction
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.data.repository.user.UserRepository
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository

class SignInUC(
    private val userRepository: UserRepository,
    private val preferencesRepository: PersonalPreferencesRepository,
) {

    suspend operator fun invoke(
        email:String,
        password:String
    ):Result<NavAction, AuthError>{
        val response = userRepository.signIn(email, password)
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