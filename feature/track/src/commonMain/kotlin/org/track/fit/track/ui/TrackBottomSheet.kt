package org.track.fit.track.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.track.fit.track.ui.state.PedometerState
import org.track.fit.ui.components.buttons.TFButton
import org.track.fit.ui.components.buttons.defaultButtonColors
import org.track.fit.ui.theme.LocalNavigationPaddings
import org.track.fit.ui.theme.localColors
import trackfit.feature.track.generated.resources.Res
import trackfit.feature.track.generated.resources.tracks_stop_btn

@Composable
internal fun TrackBottomSheet(
    modifier: Modifier = Modifier,
    onStop:()->Unit = {},
    state:PedometerState,
){

    val navigationPaddings = LocalNavigationPaddings.current

    Column(
        modifier = modifier
            .background(MaterialTheme.localColors.surfaceContainer)
            .padding(
                bottom = navigationPaddings.calculateBottomPadding(),
                start = navigationPaddings.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                end = navigationPaddings.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
                )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(top = 8.dp))

        Box(
            modifier = Modifier
                .width(52.dp)
                .height(2.dp)
                .background(MaterialTheme.localColors.outline)
                .clip(RoundedCornerShape(100))
        )

        Spacer(Modifier.height(8.dp))

        QuestInfoCard(
            state = state
        )

        Spacer(Modifier.height(8.dp))

        TFButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            title = stringResource(Res.string.tracks_stop_btn),
            onClick = onStop,
            colors = defaultButtonColors(
                container = MaterialTheme.localColors.secondaryContainer,
                content = MaterialTheme.localColors.onSecondaryContainer
            )
        )

        Spacer(Modifier.height(16.dp))

    }
}