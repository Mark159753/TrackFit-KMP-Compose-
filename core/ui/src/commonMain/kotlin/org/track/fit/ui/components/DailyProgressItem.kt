package org.track.fit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.isoDayNumber
import org.jetbrains.compose.resources.stringResource
import org.track.fit.common.date.now
import org.track.fit.common.date.toShortDayName
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.today

@Composable
fun DailyProgressItem(
    modifier: Modifier = Modifier,
    dateTime: LocalDate,
    progress:Float
){

    val today = remember {
        LocalDateTime.now()
    }
    val isToday = remember(today, dateTime) {
        dateTime == today.date
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                progress = progress,
                color = MaterialTheme.localColors.primary,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                backgroundColor = MaterialTheme.localColors.surfaceDim,
                modifier = Modifier
                    .fillMaxSize()
            )

            Text(
                text = dateTime.dayOfMonth.toString(),
                style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                color = MaterialTheme.localColors.onSurface,
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(
                if (isToday) Res.string.today
                else dateTime.dayOfWeek.isoDayNumber.toShortDayName()
            ),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.localColors.outline,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}