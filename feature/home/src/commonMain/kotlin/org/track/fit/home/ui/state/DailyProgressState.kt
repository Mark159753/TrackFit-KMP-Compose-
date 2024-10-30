package org.track.fit.home.ui.state

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.track.fit.common.date.getCurrentWeekDays
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.data.repository.statistics.StatisticsRepository

@Stable
interface DailyProgressState {

    val dailyProgress:StateFlow<List<Pair<LocalDate, Float>>>
}

class DailyProgressStateImpl(
    statisticsRepository: StatisticsRepository,
    personalPreferencesRepository: PersonalPreferencesRepository,
    scope:CoroutineScope
):DailyProgressState{

    private val dates = LocalDateTime.Companion.getCurrentWeekDays()

    override val dailyProgress: StateFlow<List<Pair<LocalDate, Float>>> = combine(
        statisticsRepository.getAchievementsInRange(
            from = dates.first(),
            to = dates.last()
        ),
        personalPreferencesRepository.preferenceFlow(PersonalPreferences.StepsGoal)
    ){ achievements, goal ->
        val intGoal = goal?.toIntOrNull() ?: 1
        achievements.map { item ->
            item.date to ((item.achievements?.steps ?: 0) / intGoal.toFloat())
        }
    }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = dates.map { it to 0f }
        )
}