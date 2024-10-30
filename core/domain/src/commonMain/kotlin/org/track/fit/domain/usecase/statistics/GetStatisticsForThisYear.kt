package org.track.fit.domain.usecase.statistics

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.track.fit.common.date.now
import org.track.fit.data.models.DateAchievements
import org.track.fit.data.repository.statistics.StatisticsRepository

class GetStatisticsForThisYear(
    private val statisticsRepository: StatisticsRepository
) {

    operator fun invoke(): Flow<List<DateAchievements>> {
        val currentYear = LocalDateTime.Companion.now().year

        val firstDayOfYear = LocalDate(currentYear, 1, 1)
        val lastDayOfYear = LocalDate(currentYear, 12, 31)

        return statisticsRepository.getAchievementsInRange(
            from = firstDayOfYear,
            to = lastDayOfYear
        )
    }

}