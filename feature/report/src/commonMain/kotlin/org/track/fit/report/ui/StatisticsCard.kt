package org.track.fit.report.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.domain.usecase.statistics.Period
import org.track.fit.domain.usecase.statistics.StatisticDataType
import org.track.fit.report.ui.state.StatisticsGraphState
import org.track.fit.ui.components.Graph
import org.track.fit.ui.theme.localColors
import org.track.fit.ui.theme.windowSize
import trackfit.core.common.generated.resources.arrow_down_ic
import trackfit.feature.report.generated.resources.Res
import trackfit.feature.report.generated.resources.report_statistic_calories
import trackfit.feature.report.generated.resources.report_statistic_distance
import trackfit.feature.report.generated.resources.report_statistic_steps
import trackfit.core.common.generated.resources.Res as SharedRes
import trackfit.feature.report.generated.resources.report_statistic_this_week
import trackfit.feature.report.generated.resources.report_statistic_this_year
import trackfit.feature.report.generated.resources.report_statistic_time
import trackfit.feature.report.generated.resources.report_statistic_title

@Composable
fun StatisticsCard(
    modifier: Modifier = Modifier,
    state: StatisticsGraphState
){
    val statisticDataType by state.statisticDataType.collectAsStateWithLifecycle()
    val statistics by state.statistics.collectAsStateWithLifecycle()
    val period by state.statisticPeriod.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.localColors.surfaceContainer)
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 300.dp, max = 340.dp)
    ) {
        CardHeadline(
            period = period,
            onCLick = state::changeStatisticPeriod
        )

        Spacer(Modifier.height(12.dp))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.localColors.outline
        )

        Spacer(Modifier.height(12.dp))

        Graph(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            scale = statistics.scale,
            list = statistics.items
        )

        CardBtns(
            statisticDataType = statisticDataType,
            onCLick = state::changeStatisticDataType,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CardHeadline(
    modifier: Modifier = Modifier,
    period: Period = Period.ThisWeek,
    onCLick: (Period) -> Unit = {}
){

    var expandMenu by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.report_statistic_title),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.localColors.onBackground
        )

        OutlineBtn(
            onCLick = { expandMenu = true }
        ){ isSelected ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(period.getUiName()),
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold),
                    color = if (isSelected) MaterialTheme.localColors.onPrimary else MaterialTheme.localColors.onBackground
                )

                Spacer(Modifier.width(4.dp))

                Icon(
                    modifier = Modifier,
                    painter = painterResource(SharedRes.drawable.arrow_down_ic),
                    contentDescription = null,
                    tint = MaterialTheme.localColors.onBackground
                )
            }

            DropdownMenu(
                expanded = expandMenu,
                onDismissRequest = { expandMenu = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        expandMenu = false
                        onCLick(Period.ThisWeek)
                    }
                ){  Text(stringResource(Res.string.report_statistic_this_week)) }
                Divider(
                    modifier = modifier.padding(horizontal = 8.dp)
                )
                DropdownMenuItem(
                    onClick = {
                        expandMenu = false
                        onCLick(Period.ThisYear)
                    }
                ){ Text(stringResource(Res.string.report_statistic_this_year)) }
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CardBtns(
    modifier: Modifier = Modifier,
    statisticDataType: StatisticDataType = StatisticDataType.Steps,
    onCLick: (StatisticDataType) -> Unit
){

    if (MaterialTheme.windowSize.widthSizeClass >= WindowWidthSizeClass.Medium){
        Row(
            modifier = modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatisticDataType.entries.forEach { c ->
                OutlineBtn(
                    isSelected = statisticDataType == c,
                    onCLick = { onCLick(c) }
                ){ isSelected ->
                    Text(
                        text = stringResource(c.getUiName()),
                        style = MaterialTheme.typography.body2,
                        color = if (isSelected) MaterialTheme.localColors.onPrimary else MaterialTheme.localColors.onBackground
                    )
                }
            }
        }
    }else{
        FlowRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatisticDataType.entries.forEach { c ->
                OutlineBtn(
                    isSelected = statisticDataType == c,
                    onCLick = { onCLick(c) }
                ){ isSelected ->
                    Text(
                        text = stringResource(c.getUiName()),
                        style = MaterialTheme.typography.body2,
                        color = if (isSelected) MaterialTheme.localColors.onPrimary else MaterialTheme.localColors.onBackground
                    )
                }
            }

        }
    }

}



@Composable
private fun OutlineBtn(
    modifier: Modifier = Modifier,
    onCLick:()->Unit = {},
    isSelected:Boolean = false,
    content:@Composable (Boolean)->Unit
){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .then(
                if (isSelected){
                    Modifier
                        .background(MaterialTheme.localColors.primary)
                }else{
                    Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.localColors.outline,
                            shape = RoundedCornerShape(100)
                        )
                }
            )
            .clickable { onCLick() }
            .padding(horizontal = 14.dp, vertical = 5.dp)
    ) {
        content(isSelected)
    }
}


fun StatisticDataType.getUiName() = when(this){
    StatisticDataType.Steps -> Res.string.report_statistic_steps
    StatisticDataType.Duration -> Res.string.report_statistic_time
    StatisticDataType.Calories -> Res.string.report_statistic_calories
    StatisticDataType.Distance -> Res.string.report_statistic_distance
}

fun Period.getUiName() = when(this){
    Period.ThisWeek -> Res.string.report_statistic_this_week
    Period.ThisYear -> Res.string.report_statistic_this_year
}
