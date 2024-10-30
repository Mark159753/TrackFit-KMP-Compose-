package org.track.fit.report.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.data.repository.statistics.StatisticsRepository
import org.track.fit.domain.usecase.statistics.GetStatisticsByDataType
import org.track.fit.domain.usecase.statistics.GetStatisticsFor
import org.track.fit.report.ui.state.ReportCalendarStateImpl
import org.track.fit.report.ui.state.ReportStateImpl
import org.track.fit.report.ui.state.StatisticsGraphStateImpl
import org.track.fit.report.ui.state.TotalStateImpl

class ReportViewModel(
    statisticsRepository: StatisticsRepository,
    getStatisticsFor: GetStatisticsFor,
    getStatisticsByDataType: GetStatisticsByDataType,
    personalPreferencesRepository: PersonalPreferencesRepository
):ViewModel() {

    val state = ReportStateImpl(
        totalState = TotalStateImpl(
            statisticsRepository = statisticsRepository,
            scope = viewModelScope
        ),
        statisticsGraphState = StatisticsGraphStateImpl(
            getStatisticsFor = getStatisticsFor,
            getStatisticsByDataType = getStatisticsByDataType,
            scope = viewModelScope
        ),
        reportCalendarState = ReportCalendarStateImpl(
            statisticsRepository = statisticsRepository,
            personalPreferencesRepository = personalPreferencesRepository,
            scope = viewModelScope
        )
    )
}