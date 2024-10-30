package org.track.fit.domain.usecase.statistics

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import org.track.fit.common.date.getCurrentWeekDays
import org.track.fit.data.models.DateAchievements
import org.track.fit.data.repository.statistics.StatisticsRepository

class GetStatisticsForThisWeek(
    private val statisticsRepository: StatisticsRepository
) {

    operator fun invoke(): Flow<List<DateAchievements>> {
        val dates = LocalDateTime.Companion.getCurrentWeekDays()
        return statisticsRepository.getAchievementsInRange(
            from = dates.first(),
            to = dates.last()
        )
    }
}