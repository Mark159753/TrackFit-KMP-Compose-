package org.track.fit.domain.validation.base

import org.jetbrains.compose.resources.StringResource

data class ValidateResult(
    val isSuccess: Boolean,
    val message: StringResource?
)
