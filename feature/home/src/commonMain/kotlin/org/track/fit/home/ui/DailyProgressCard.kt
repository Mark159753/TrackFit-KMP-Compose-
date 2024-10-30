package org.track.fit.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.track.fit.home.ui.state.DailyProgressState
import org.track.fit.ui.components.DailyProgressItem
import org.track.fit.ui.theme.localColors
import trackfit.feature.home.generated.resources.Res
import trackfit.feature.home.generated.resources.home_your_progress_title

@Composable
fun DailyProgressCard(
    modifier: Modifier = Modifier,
    state: DailyProgressState
){
    val daysOfWeek by state.dailyProgress.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.localColors.surfaceContainer)
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(Res.string.home_your_progress_title),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.localColors.onSurface
        )

        Spacer(Modifier.height(14.dp))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.localColors.outline
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            daysOfWeek.forEachIndexed { index, day ->
                DailyProgressItem(
                    modifier = Modifier
                        .weight(1f),
                    dateTime = day.first,
                    progress = day.second
                )
                if (index != daysOfWeek.size - 1){
                    Spacer(Modifier.width(7.dp))
                }
            }
        }
    }

}