package org.track.fit.report.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.track.fit.report.ui.state.ReportCalendarState
import org.track.fit.ui.theme.localColors
import trackfit.feature.report.generated.resources.Res
import trackfit.feature.report.generated.resources.report_statistic_your_progress

@Composable
fun CalendarCard(
    modifier: Modifier = Modifier,
    state: ReportCalendarState
){
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.localColors.surfaceContainer)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(Res.string.report_statistic_your_progress),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.localColors.onBackground
        )

        Spacer(Modifier.height(12.dp))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.localColors.outline
        )

        ProgressCalendar(
            state = state
        )

    }
}