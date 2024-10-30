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
import org.track.fit.registration.ui.state.StepsGoalState
import org.track.fit.ui.components.VerticalWheelPicker
import org.track.fit.ui.theme.localColors
import trackfit.feature.registration.generated.resources.Res
import trackfit.feature.registration.generated.resources.registration_choose_your_goal
import trackfit.feature.registration.generated.resources.registration_set_your
import trackfit.feature.registration.generated.resources.registration_step_goal
import trackfit.feature.registration.generated.resources.registration_steps_unit

@Composable
fun SetupGoalStep(
    modifier: Modifier = Modifier,
    state: StepsGoalState,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val title = buildAnnotatedString {
            append(stringResource(Res.string.registration_set_your))
            append(" ")
            withStyle(
                SpanStyle(color = MaterialTheme.localColors.primary)
            ){
                append(stringResource(Res.string.registration_step_goal))
            }
        }
        StepTitle(
            title = title,
            subtitle = stringResource(Res.string.registration_choose_your_goal),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        VerticalWheelPicker(
            modifier = Modifier.fillMaxSize(),
            items = state.items,
            startPosition = state.currentPosition.value,
            unit = stringResource(Res.string.registration_steps_unit),
            onPositionChanged = state::setCurrentPosition
        )

    }
}