package org.track.fit.report.ui.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.data.repository.statistics.StatisticsRepository
import org.track.fit.report.ui.calendar.ProgressCalendarState

@Stable
interface ReportCalendarState {

    val calendarState:ProgressCalendarState

    fun getProgressFromPage(date: LocalDate):StateFlow<Map<Int,List<DayWithProgress>>>

}

class ReportCalendarStateImpl(
    private val statisticsRepository: StatisticsRepository,
    private val personalPreferencesRepository: PersonalPreferencesRepository,
    private val scope: CoroutineScope
):ReportCalendarState{

    override val calendarState = ProgressCalendarState()

    override fun getProgressFromPage(date: LocalDate): StateFlow<Map<Int,List<DayWithProgress>>> {
        val page = calendarState.getPageDays(date)
        val start = page[0]!!.first()
        val end = page[4]!!.last()

        val daysWithProgress = page.mapValues {
            it.value.map { d -> DayWithProgress(d) }
        }
        return combine(
            statisticsRepository.getAchievementsInRange(
                from = start,
                to = end
            ),
            personalPreferencesRepository.preferenceFlow(PersonalPreferences.StepsGoal)
        ){ achievements, goal ->
            val intGoal = goal?.toIntOrNull() ?: 1
            achievements.map { item ->
                DayWithProgress(
                    day = item.date,
                    progress = ((item.achievements?.steps ?: 0) / intGoal.toFloat())
                )
            }.chunked(7)
            .mapIndexed { index, sub -> index to sub}
            .toMap()
        }.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = daysWithProgress
        )
    }
}

@Immutable
data class DayWithProgress(
    val day:LocalDate,
    val progress:Float = 0f
)