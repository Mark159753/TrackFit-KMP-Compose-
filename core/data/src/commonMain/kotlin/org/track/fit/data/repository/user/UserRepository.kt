package org.track.fit.data.repository.user

import kotlinx.coroutines.flow.Flow
import org.track.fit.common.provider.GoogleAccount
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.data.models.UserModel

interface UserRepository {

    val user:Flow<UserModel?>

    suspend fun signIn(email:String, password:String): Result<UserModel, AuthError>

    suspend fun signUp(email:String, password: String):Result<UserModel, AuthError>

    suspend fun loginWithGoogle(googleAccount:GoogleAccount?): Result<UserModel, AuthError>

    suspend fun signOut():AuthError?

}