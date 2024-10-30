package org.track.fit.registration.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.track.fit.registration.ui.state.AgeState
import org.track.fit.ui.components.VerticalWheelPicker
import org.track.fit.ui.theme.localColors
import trackfit.feature.registration.generated.resources.Res
import trackfit.feature.registration.generated.resources.registration_are_you
import trackfit.feature.registration.generated.resources.registration_how
import trackfit.feature.registration.generated.resources.registration_old
import trackfit.feature.registration.generated.resources.registration_share_age
import trackfit.feature.registration.generated.resources.registration_years_unit

@Composable
fun AgeStep(
    modifier: Modifier = Modifier,
    state: AgeState
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val title = buildAnnotatedString {
            append(stringResource(Res.string.registration_how))
            append(" ")
            withStyle(
                SpanStyle(color = MaterialTheme.localColors.primary)
            ){
                append(stringResource(Res.string.registration_old))
            }
            append(" ")
            append(stringResource(Res.string.registration_are_you))
        }
        StepTitle(
            title = title,
            subtitle = stringResource(Res.string.registration_share_age),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        VerticalWheelPicker(
            modifier = Modifier.fillMaxSize(),
            items = state.items,
            startPosition = 10,
            unit = stringResource(Res.string.registration_years_unit),
            onPositionChanged = state::setCurrentPosition
        )

    }
}