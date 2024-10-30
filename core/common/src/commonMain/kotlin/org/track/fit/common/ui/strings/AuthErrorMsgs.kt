package org.track.fit.common.ui.strings

import org.track.fit.common.result.AuthError
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.auth_exist_user_error
import trackfit.core.common.generated.resources.auth_invalid_email_error
import trackfit.core.common.generated.resources.auth_invalid_password_error
import trackfit.core.common.generated.resources.auth_weak_password_error
import trackfit.core.common.generated.resources.unknown_error

fun AuthError.toUiText() = when(this){
    AuthError.InvalidEmail -> UIText.ResString(Res.string.auth_invalid_email_error)
    AuthError.InvalidPassword -> UIText.ResString(Res.string.auth_invalid_password_error)
    AuthError.UnknownError -> UIText.ResString(Res.string.unknown_error)
    AuthError.WeakPassword -> UIText.ResString(Res.string.auth_weak_password_error)
    AuthError.UserAlreadyExist -> UIText.ResString(Res.string.auth_exist_user_error)
    AuthError.Cancel -> UIText.DynamicString("")
}