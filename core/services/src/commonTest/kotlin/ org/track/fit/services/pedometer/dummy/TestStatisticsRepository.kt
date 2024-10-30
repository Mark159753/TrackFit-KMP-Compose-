package  org.track.fit.services.pedometer.dummy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDate
import org.track.fit.data.models.DailyAchievements
import org.track.fit.data.models.DateAchievements
import org.track.fit.data.repository.statistics.StatisticsRepository

class TestStatisticsRepository(
    initValue: DailyAchievements? = null
): StatisticsRepository {

    private val _achievements = MutableStateFlow<DailyAchievements?>(initValue)
    override val todayAchievements: Flow<DailyAchievements?>
        get() = _achievements

    override val total: Flow<DailyAchievements?>
        get() = TODO("Not yet implemented")

    override fun getAchievementsInRange(
        from: LocalDate,
        to: LocalDate
    ): Flow<List<DateAchievements>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTodayAchievements(data: DailyAchievements) {
        _achievements.value = data
    }
}