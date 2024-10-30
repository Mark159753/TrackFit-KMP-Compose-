package org.track.fit.registration.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.track.fit.common.actions.NavAction
import org.track.fit.registration.ui.state.RegistrationState
import org.track.fit.registration.ui.state.RegistrationStep
import org.track.fit.registration.ui.steps.AgeStep
import org.track.fit.registration.ui.steps.GenderStep
import org.track.fit.registration.ui.steps.HeightStep
import org.track.fit.registration.ui.steps.SetupGoalStep
import org.track.fit.registration.ui.steps.WeightStep
import org.track.fit.ui.components.LifeCycleActions
import org.track.fit.ui.components.buttons.TFButton
import org.track.fit.ui.components.buttons.defaultButtonColors
import org.track.fit.ui.theme.localColors
import org.track.fit.ui.util.actions.UIActions
import trackfit.feature.registration.generated.resources.Res
import trackfit.feature.registration.generated.resources.registration_continue_btn
import trackfit.feature.registration.generated.resources.registration_finish_btn
import trackfit.feature.registration.generated.resources.registration_skip_btn

@Composable
fun RegistrationScreen(
    state:RegistrationState,
    uiActions: UIActions,
    onNavToHome:()->Unit,
){
    LifeCycleActions(uiActions.actions){ action ->
        when(action){
            NavAction.NavToHome -> onNavToHome()
            else -> {}
        }
    }

    val stepInfo by state.stepInfo.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .background(MaterialTheme.localColors.surface)
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(Modifier.height(16.dp))


        StepsProgress(
            steps = stepInfo.stepsCount,
            currentStep = stepInfo.position + 1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        AnimatedContent(
            targetState = stepInfo,
            modifier = Modifier
                .weight(1f),
            transitionSpec = {
                if (targetState.position > initialState.position) {
                    slideInHorizontally(
                        animationSpec = tween(CONTENT_ANIMATION_DURATION),
                        initialOffsetX = { fullWidth -> fullWidth }
                    ) togetherWith
                            slideOutHorizontally(
                                animationSpec = tween(CONTENT_ANIMATION_DURATION),
                                targetOffsetX = { fullWidth -> -fullWidth }
                            )
                } else {
                    slideInHorizontally(
                        animationSpec = tween(CONTENT_ANIMATION_DURATION),
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ) togetherWith
                            slideOutHorizontally(
                                animationSpec = tween(CONTENT_ANIMATION_DURATION),
                                targetOffsetX = { fullWidth -> fullWidth }
                            )
                }
            }
        ) { step ->
            when(step.current){
                RegistrationStep.YourGender -> GenderStep(state = state.genderState)
                RegistrationStep.YourAge -> AgeStep(state = state.ageState)
                RegistrationStep.YourHeight -> HeightStep(state = state.heightState)
                RegistrationStep.YourWeight -> WeightStep(state = state.weightState)
                RegistrationStep.YourGoal -> SetupGoalStep(state = state.goalState)
            }
        }

        StepButtons(
            onSkip = state::onSkip,
            onContinue = state::onContinue,
            haveNext = stepInfo.haveNext,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun StepButtons(
    modifier: Modifier = Modifier,
    onSkip:()->Unit,
    onContinue:()->Unit,
    haveNext:Boolean = true
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (haveNext) {
            TFButton(
                modifier = Modifier
                    .weight(1f),
                title = stringResource(Res.string.registration_skip_btn),
                onClick = onSkip,
                colors = defaultButtonColors(
                    container = MaterialTheme.localColors.secondaryContainer,
                    content = MaterialTheme.localColors.onSecondaryContainer
                )
            )
            Spacer(Modifier.width(16.dp))
        }

        TFButton(
            modifier = Modifier
                .weight(1f),
            title = if (haveNext) stringResource(Res.string.registration_continue_btn) else
                stringResource(Res.string.registration_finish_btn),
            onClick = onContinue
        )

    }
}

@Composable
private fun StepsProgress(
    modifier: Modifier = Modifier,
    steps:Int,
    currentStep:Int
){

    val p by animateFloatAsState(targetValue = (currentStep / steps.toFloat()).coerceIn(0f, 1f))

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinearProgressIndicator(
            progress = p,
            color = MaterialTheme.localColors.primary,
            backgroundColor = MaterialTheme.localColors.surfaceDim,
            modifier = Modifier
                .height(12.dp)
                .weight(1f),
            strokeCap = StrokeCap.Round
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = "$currentStep/$steps",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.localColors.onSurface
        )
    }
}

private const val CONTENT_ANIMATION_DURATION = 300