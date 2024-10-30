package org.track.fit.data.repository.statistics

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import org.track.fit.data.models.DailyAchievements
import org.track.fit.data.models.DateAchievements

interface StatisticsRepository {

    val todayAchievements: Flow<DailyAchievements?>

    val total:Flow<DailyAchievements?>

    suspend fun saveTodayAchievements(data:DailyAchievements)

    fun getAchievementsInRange(from: LocalDate, to:LocalDate):Flow<List<DateAchievements>>
}

internal const val DAILY_ACHIEVEMENT = "/DailyAchievements"