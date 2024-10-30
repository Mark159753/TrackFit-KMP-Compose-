package org.track.fit.common.result

sealed interface AuthError:Error {
    data object UnknownError:AuthError
    data object InvalidPassword:AuthError
    data object InvalidEmail:AuthError
    data object WeakPassword:AuthError
    data object UserAlreadyExist:AuthError
    data object Cancel:AuthError
}