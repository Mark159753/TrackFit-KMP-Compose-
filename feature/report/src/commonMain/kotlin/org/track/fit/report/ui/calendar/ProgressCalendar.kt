package org.track.fit.report.ui.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.number
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.common.ui.strings.UIText
import org.track.fit.report.ui.state.DayWithProgress
import org.track.fit.report.ui.state.ReportCalendarState
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.arrow_back_ios_ic
import trackfit.core.common.generated.resources.arrow_forward_ios_ic

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProgressCalendar(
    modifier: Modifier = Modifier,
    state: ReportCalendarState
){

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = state.calendarState.startIndex
    ){ state.calendarState.totalMonth }


    val headline by remember(pagerState.currentPage) {
        mutableStateOf(state.calendarState.getMonthYearHeadLine(pagerState.currentPage))
    }

    Column(
        modifier = modifier,
    ) {

        CalendarHeadline(
            modifier = Modifier.fillMaxWidth(),
            onNext = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage +1)
                }
            },
            onPrevious = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage -1)
                }
            },
            title = headline
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ){ index ->
            CalendarPage(
                modifier = Modifier.fillMaxWidth(),
                index = index,
                state = state
            )
        }
    }

}

@Composable
private fun CalendarHeadline(
    modifier: Modifier = Modifier,
    title:UIText,
    onNext:()->Unit,
    onPrevious:()->Unit
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onPrevious,
            modifier = Modifier.size(50.dp)
        ){
            Icon(
                painter = painterResource(Res.drawable.arrow_back_ios_ic),
                contentDescription = null,
                tint = MaterialTheme.localColors.onBackground
            )
        }

        Text(
            text = title.asString(),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.localColors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onNext,
            modifier = Modifier.size(55.dp)
        ){
            Icon(
                painter = painterResource(Res.drawable.arrow_forward_ios_ic),
                contentDescription = null,
                tint = MaterialTheme.localColors.onBackground
            )
        }
    }
}

@Composable
private fun CalendarPage(
    modifier: Modifier = Modifier,
    index:Int,
    state: ReportCalendarState
){
    val monthDate = remember(index) {
        state.calendarState.getLocalDateForIndex(index)
    }

    val days = remember {
        mutableStateMapOf<Int,List<DayWithProgress>>()
    }

    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(monthDate, lifecycle){
        state.getProgressFromPage(monthDate)
            .flowWithLifecycle(lifecycle.lifecycle)
            .collectLatest { result ->
                days.clear()
                days.putAll(result)
            }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            state.calendarState.dayNames.forEach { name ->
                Text(
                    text = stringResource(name),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.localColors.onBackground,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        repeat(days.size){ i ->
            val row = days[i]!!
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                row.forEach { dayWithProgress ->
                    CalenderDayItem(
                        modifier = Modifier
                            .weight(1f),
                        day = dayWithProgress.day.dayOfMonth,
                        progress = dayWithProgress.progress,
                        isCurrent = dayWithProgress.day.month.number == monthDate.month.number
                    )
                }
            }
        }
    }
}

@Composable
private fun CalenderDayItem(
    modifier: Modifier = Modifier,
    progress:Float,
    day:Int,
    isCurrent:Boolean = true
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
    ) {
        CircularProgressIndicator(
            progress = progress,
            color = if (isCurrent) MaterialTheme.localColors.primary else MaterialTheme.localColors.circularIndicator,
            strokeWidth = 3.dp,
            strokeCap = StrokeCap.Round,
            backgroundColor = if (isCurrent) MaterialTheme.localColors.surfaceDim else MaterialTheme.localColors.circularBackground,
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = day.toString(),
            style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
            color = if (isCurrent) MaterialTheme.localColors.onSurface else MaterialTheme.localColors.circularText
        )
    }
}