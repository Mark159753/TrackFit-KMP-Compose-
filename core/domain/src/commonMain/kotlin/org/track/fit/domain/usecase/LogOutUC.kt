package org.track.fit.domain.usecase

import org.track.fit.common.actions.NavAction
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result

expect class LogOutUC {

    suspend operator fun invoke(): Result<NavAction, AuthError>
}