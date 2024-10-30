package org.track.fit.report.ui.state

import androidx.compose.runtime.Stable

@Stable
interface ReportState:TotalState, StatisticsGraphState, ReportCalendarState{


}

class ReportStateImpl(
    totalState: TotalState,
    statisticsGraphState: StatisticsGraphState,
    reportCalendarState: ReportCalendarState
):ReportState,
        TotalState by totalState,
        StatisticsGraphState by statisticsGraphState,
        ReportCalendarState by reportCalendarState