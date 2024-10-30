package org.track.fit.domain.usecase.statistics

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.track.fit.data.models.DailyAchievements
import org.track.fit.data.models.DateAchievements

class GetStatisticsFor(
    private val getStatisticsForThisWeek: GetStatisticsForThisWeek,
    private val getStatisticsForThisYear: GetStatisticsForThisYear
) {

    operator fun invoke(period: Period): Flow<List<StatisticsFor>> {
        return getDateAchievementsFlow(period)
            .map { list ->
                list.toStatisticsFor(period)
            }
    }

    private fun getDateAchievementsFlow(period: Period) = when(period){
        Period.ThisWeek -> getStatisticsForThisWeek()
        Period.ThisYear -> getStatisticsForThisYear()
    }

    private fun List<DateAchievements>.toStatisticsFor(period: Period) = when(period){
        Period.ThisWeek -> statisticsForThisWeek(this)
        Period.ThisYear -> statisticsForThisYear(this)
    }

    private fun statisticsForThisWeek(list:List<DateAchievements>): List<StatisticsFor> {
        return list.map { item ->
            StatisticsFor(
                periodType = PeriodType.Day(item.date.dayOfMonth),
                data = item.achievements
            )
        }
    }

    private fun statisticsForThisYear(list:List<DateAchievements>): List<StatisticsFor> {
        return list
            .groupBy { it.date.monthNumber }
            .map { map ->
                val achievements = map.value
                    .mapNotNull { it.achievements }
                    .reduceOrNull { acc, dailyAchievements ->
                        DailyAchievements(
                            steps = acc.steps + dailyAchievements.steps,
                            kcal =  acc.kcal + dailyAchievements.kcal,
                            distance = acc.distance + dailyAchievements.distance,
                            duration = acc.duration + dailyAchievements.duration
                        )
                }

                StatisticsFor(
                    periodType = PeriodType.Month(map.key),
                    data = achievements
                )
            }
    }

}

@Immutable
data class StatisticsFor(
    val periodType: PeriodType,
    val data: DailyAchievements?
)

enum class Period{
    ThisWeek, ThisYear
}

sealed interface PeriodType{

    val value:Int

    data class Day(override val value:Int):PeriodType
    data class Month(override val value:Int):PeriodType
}