package org.track.fit.common.constants

import org.jetbrains.compose.resources.StringResource
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.en_language
import trackfit.core.common.generated.resources.uk_language

enum class AppLanguage(
    val displayName:StringResource
) {
    EN(
        displayName = Res.string.en_language
    ),
    UK(
        displayName = Res.string.uk_language
    )
}