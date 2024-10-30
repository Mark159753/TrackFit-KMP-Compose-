package org.track.fit.domain.validation

import org.track.fit.common.constants.EMAIL_PATTERN
import org.track.fit.domain.validation.base.IValidator
import org.track.fit.domain.validation.base.ValidateResult
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.valid_field_wrong_email_error

class EmailValidator() : IValidator {
    override fun validate(input:String): ValidateResult {
        val isValid = EMAIL_PATTERN.matches(input)
        return ValidateResult(
            isValid,
            if (isValid) null else Res.string.valid_field_wrong_email_error
        )
    }
}