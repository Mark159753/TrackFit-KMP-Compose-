package org.track.fit.domain.validation

import org.track.fit.domain.validation.base.IValidator
import org.track.fit.domain.validation.base.ValidateResult
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.valid_field_empty_error

class EmptyValidator() : IValidator {
    override fun validate(input:String): ValidateResult {
        val isValid = input.isNotEmpty()
        return ValidateResult(
            isValid,
            if (isValid) null else Res.string.valid_field_empty_error
        )
    }
}