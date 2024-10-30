package org.track.fit.report.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.track.fit.report.ui.calendar.CalendarCard
import org.track.fit.report.ui.state.ReportState
import org.track.fit.ui.components.IntrinsicMinRow
import org.track.fit.ui.components.TFToolBar
import org.track.fit.ui.theme.LocalNavigationPaddings
import org.track.fit.ui.theme.localColors
import org.track.fit.ui.theme.windowSize
import trackfit.feature.report.generated.resources.Res
import trackfit.feature.report.generated.resources.report_title

@Composable
fun ReportScreen(
    state:ReportState
){
    val navigationPaddings = LocalNavigationPaddings.current

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.localColors.surface)
            .statusBarsPadding()
            .padding(
                start = navigationPaddings.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                end = navigationPaddings.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
            )
            .fillMaxSize(),
        backgroundColor = MaterialTheme.localColors.surface,
        topBar = {
            TFToolBar(
                title = stringResource(Res.string.report_title),
                modifier = Modifier
                    .background(MaterialTheme.localColors.surface)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = navigationPaddings.calculateBottomPadding())
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TotalCard(
                modifier = Modifier.fillMaxWidth(),
                state = state
            )

            Spacer(Modifier.height(16.dp))

            if (MaterialTheme.windowSize.widthSizeClass >= WindowWidthSizeClass.Medium){
                MediumStatisticsContent(state = state)
            }else{
                CompactStatisticsContent(state = state)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CompactStatisticsContent(
    modifier: Modifier = Modifier,
    state: ReportState
){
    Column(
        modifier = modifier
    ) {
        StatisticsCard(
            modifier = Modifier,
            state = state
        )

        Spacer(Modifier.height(16.dp))

        CalendarCard(
            state = state
        )
    }
}

@Composable
private fun MediumStatisticsContent(
    modifier: Modifier = Modifier,
    state: ReportState
){
    IntrinsicMinRow(
        modifier = modifier
            .fillMaxWidth(),
        spaceBy = 16.dp
    ) {

        StatisticsCard(
            modifier = Modifier,
            state = state
        )

        CalendarCard(
            state = state,
            modifier = Modifier.intrinsicMin()
        )
    }
}