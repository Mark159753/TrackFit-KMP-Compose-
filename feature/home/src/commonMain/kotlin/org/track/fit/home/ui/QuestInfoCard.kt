package org.track.fit.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.track.fit.home.ui.state.PedometerState
import org.track.fit.ui.components.ColumnInfoItem
import org.track.fit.ui.components.columnInfoDefaults
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.kcal
import trackfit.core.common.generated.resources.kcal_burn_ic
import trackfit.core.common.generated.resources.km
import trackfit.core.common.generated.resources.location_ic
import trackfit.core.common.generated.resources.time
import trackfit.core.common.generated.resources.Res as SharedRes
import trackfit.core.common.generated.resources.time_ic

@Composable
fun QuestInfoCard(
    modifier: Modifier = Modifier,
    state: PedometerState
){

    val pedometerData by state.pedometerData.collectAsStateWithLifecycle()

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.localColors.surfaceContainer)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ColumnInfoItem(
            modifier = Modifier
                .weight(1f),
            colors = columnInfoDefaults(iconTint = MaterialTheme.localColors.timeIcon),
            icon = SharedRes.drawable.time_ic,
            title = stringResource(SharedRes.string.time),
            value = pedometerData.duration.asString()
        )


        Box(
            Modifier
                .background(MaterialTheme.localColors.outline)
                .width(1.dp)
                .fillMaxHeight()
        )

        ColumnInfoItem(
            modifier = Modifier
                .weight(1f),
            colors = columnInfoDefaults(iconTint = MaterialTheme.localColors.kcalIcon),
            icon = SharedRes.drawable.kcal_burn_ic,
            title = stringResource(SharedRes.string.kcal),
            value = pedometerData.kcal
        )

        Box(
            Modifier
                .background(MaterialTheme.localColors.outline)
                .width(1.dp)
                .fillMaxHeight()
        )

        ColumnInfoItem(
            modifier = Modifier
                .weight(1f),
            colors = columnInfoDefaults(iconTint = MaterialTheme.localColors.distanceIcon),
            icon = SharedRes.drawable.location_ic,
            title = stringResource(SharedRes.string.km),
            value = pedometerData.distance
        )
    }
}