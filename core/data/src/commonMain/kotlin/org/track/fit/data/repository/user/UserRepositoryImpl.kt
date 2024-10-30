package org.track.fit.data.repository.user

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import dev.gitlive.firebase.auth.FirebaseAuthWeakPasswordException
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.track.fit.common.provider.GoogleAccount
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.data.mappers.toModel
import org.track.fit.data.models.UserModel
import kotlin.coroutines.cancellation.CancellationException
import dev.gitlive.firebase.auth.GoogleAuthProvider as AuthProvider

class UserRepositoryImpl : UserRepository {

    private val auth = Firebase.auth

    override val user: Flow<UserModel?> = auth.authStateChanged
        .map { profile -> profile?.toModel() }

    override suspend fun signIn(email: String, password: String): Result<UserModel, AuthError>  {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password)
            val currentUser = authResult.user
            if (currentUser == null)
                Result.Error(AuthError.UnknownError)
            else
                Result.Success(currentUser.toModel())
        }catch (e:CancellationException){
            throw e
        }catch (e: FirebaseAuthInvalidUserException){
            Result.Error(AuthError.InvalidEmail)
        }catch (e: FirebaseAuthInvalidCredentialsException){
            Result.Error(AuthError.InvalidPassword)
        }catch (e: FirebaseAuthWeakPasswordException){
            Result.Error(AuthError.WeakPassword)
        }catch (e: FirebaseAuthUserCollisionException){
            Result.Error(AuthError.UserAlreadyExist)
        }catch (e:Exception){
            Logger.e(e){ "signIn" }
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<UserModel, AuthError> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password)
            val currentUser = authResult.user
            if (currentUser == null)
                Result.Error(AuthError.UnknownError)
            else
                Result.Success(currentUser.toModel())
        }catch (e:CancellationException){
            throw e
        }catch (e: FirebaseAuthInvalidUserException){
            Result.Error(AuthError.InvalidEmail)
        }catch (e: FirebaseAuthInvalidCredentialsException){
            Result.Error(AuthError.InvalidPassword)
        }catch (e: FirebaseAuthWeakPasswordException){
            Result.Error(AuthError.WeakPassword)
        }catch (e: FirebaseAuthUserCollisionException){
            Result.Error(AuthError.UserAlreadyExist)
        }catch (e:Exception){
            Logger.e(e){ "signUp" }
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun loginWithGoogle(googleAccount: GoogleAccount?): Result<UserModel, AuthError>{
        googleAccount ?: return Result.Error(AuthError.Cancel)
        return try {
            val credential = AuthProvider.credential(googleAccount.token, googleAccount.accessToken)
            val authResult = auth.signInWithCredential(credential)

            val currentUser = authResult.user
            if (currentUser == null)
                Result.Error(AuthError.UnknownError)
            else
                Result.Success(currentUser.toModel())
        }catch (e:CancellationException){
            throw e
        }catch (e: FirebaseAuthUserCollisionException){
            Result.Error(AuthError.UserAlreadyExist)
        }catch (e:Exception){
            Logger.e(e){ "loginWithGoogle" }
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun signOut():AuthError? {
        return try {
            auth.signOut()
            null
        }catch (e:CancellationException){
            throw e
        }catch (e: Exception){
            AuthError.UnknownError
        }
    }
}