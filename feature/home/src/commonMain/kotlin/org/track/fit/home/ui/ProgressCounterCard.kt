package org.track.fit.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.home.ui.state.PedometerState
import org.track.fit.ui.components.TFStepsProgressIndicator
import org.track.fit.ui.components.defaultPIColors
import org.track.fit.ui.components.defaultPITypography
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.pause_ic
import trackfit.core.common.generated.resources.play_ic
import trackfit.feature.home.generated.resources.Res
import trackfit.core.common.generated.resources.Res as SharedRes
import trackfit.feature.home.generated.resources.home_steps

@Composable
fun ProgressCounterCard(
    modifier: Modifier = Modifier,
    isRunning:Boolean = false,
    onStartClick:()->Unit = {},
    onStopClick:()->Unit = {},
    state:PedometerState
){

    val pedometerData by state.pedometerData.collectAsStateWithLifecycle()
    val stepsGoal by state.stepsGoal.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.localColors.surfaceContainer)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        TFStepsProgressIndicator(
            modifier = Modifier
                .widthIn(max = 270.dp)
                .aspectRatio(1f),
            goal = stepsGoal,
            steps = pedometerData.steps,
            button = {
                IconButton(
                    onClick = {
                        if (isRunning) onStopClick() else onStartClick()
                      },
                    modifier = Modifier
                        .clip(CircleShape)
                        .then(
                            if (isRunning){
                                Modifier
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.localColors.primary,
                                        shape = CircleShape
                                    )
                            }else{
                                Modifier
                                    .background(MaterialTheme.localColors.primary)
                            }
                        )
                ) {
                    if (isRunning) {
                        Icon(
                            painterResource(SharedRes.drawable.pause_ic),
                            contentDescription = null,
                            tint = MaterialTheme.localColors.primary
                        )
                    }else{
                        Icon(
                            painter = painterResource(SharedRes.drawable.play_ic),
                            contentDescription = null,
                            tint = MaterialTheme.localColors.onPrimary
                        )
                    }
                }
            },
            title = stringResource(Res.string.home_steps),
            thin = 22.dp,
            typography = defaultPITypography(
                title = MaterialTheme.typography.body1,
                goal = MaterialTheme.typography.body1,
                steps = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            ),
            colors = defaultPIColors(
                path = MaterialTheme.localColors.surfaceDim
            )
        )
    }
}