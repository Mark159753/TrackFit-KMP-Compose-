package org.track.fit.domain.validation

import org.track.fit.domain.validation.base.IValidator
import org.track.fit.domain.validation.base.ValidateResult
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.valid_field_long_password_error
import trackfit.core.common.generated.resources.valid_field_short_password_error

class PasswordValidator() : IValidator {
    private val minPasswordLength = 6
    private val maxPasswordLength = 12

    override fun validate(input:String): ValidateResult {
        if (input.length < minPasswordLength)
            return ValidateResult(false, Res.string.valid_field_short_password_error)
        if (input.length > maxPasswordLength)
            return ValidateResult(false, Res.string.valid_field_long_password_error)
        return ValidateResult(true, null)
    }
}