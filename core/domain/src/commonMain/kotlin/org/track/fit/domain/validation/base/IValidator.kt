package org.track.fit.domain.validation.base

import org.jetbrains.compose.resources.StringResource

interface IValidator {

    fun validate(input:String) : ValidateResult
}

fun List<IValidator>.firstErrorOrNull(value:String):StringResource?{
    for (i in this){
        val result = i.validate(value)
        if (!result.isSuccess){
            return result.message
        }
    }
    return null
}