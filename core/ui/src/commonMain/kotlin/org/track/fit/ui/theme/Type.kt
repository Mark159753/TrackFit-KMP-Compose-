package org.track.fit.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import trackfit.core.ui.generated.resources.Res
import trackfit.core.ui.generated.resources.raleway_bold
import trackfit.core.ui.generated.resources.raleway_light
import trackfit.core.ui.generated.resources.raleway_medium
import trackfit.core.ui.generated.resources.raleway_regular
import trackfit.core.ui.generated.resources.raleway_semi_bold
import trackfit.core.ui.generated.resources.raleway_thin


@Composable
internal fun getRalewayFrontFamily() = FontFamily(
    Font(
        resource = Res.font.raleway_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.raleway_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.raleway_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.raleway_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.raleway_semi_bold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.raleway_thin,
        weight = FontWeight.Thin,
        style = FontStyle.Normal
    )
)

