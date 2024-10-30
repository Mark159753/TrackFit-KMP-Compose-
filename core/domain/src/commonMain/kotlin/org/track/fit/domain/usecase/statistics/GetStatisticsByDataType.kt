package org.track.fit.domain.usecase.statistics

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
import org.track.fit.common.date.getCurrentWeekDays
import org.track.fit.common.exstantion.roundToString

class GetStatisticsByDataType {

    operator fun invoke(items:List<StatisticsFor>, type:StatisticDataType): StatisticGraphData {
        return when(type){
            StatisticDataType.Steps -> getStepsStatistics(items)
            StatisticDataType.Duration -> getDurationStatistics(items)
            StatisticDataType.Calories -> getCaloriesStatistics(items)
            StatisticDataType.Distance -> getDistanceStatistics(items)
        }
    }

    private fun getStepsStatistics(list:List<StatisticsFor>): StatisticGraphData {
        val max = list.maxBy { it.data?.steps ?: 0 }.data?.steps ?: 5
        return StatisticGraphData(
            scale = calculateGraphSteps(max.toDouble()),
            items = list.map {
                GraphData(it.periodType.value.toString(), (it.data?.steps ?: 0) / max.toFloat())
            }.toImmutableList()
        )
    }

    private fun getDurationStatistics(list:List<StatisticsFor>): StatisticGraphData {
        val max = (list.maxBy { it.data?.duration ?: 0 }.data?.duration ?: 5)
        return StatisticGraphData(
            scale = maxDuration(max),
            items = list.map {
                GraphData(it.periodType.value.toString(), (it.data?.duration ?: 0) / max.toFloat())
            }.toImmutableList()
        )
    }

    private fun getCaloriesStatistics(list:List<StatisticsFor>): StatisticGraphData {
        val max = list.maxBy { it.data?.kcal ?: 0.0 }.data?.kcal ?: 5.0
        return StatisticGraphData(
            scale = calculateGraphSteps(max),
            items = list.map { GraphData(it.periodType.value.toString(),
                ((it.data?.kcal ?: 0.0) / max).toFloat()
            ) }.toImmutableList()
        )
    }

    private fun getDistanceStatistics(list:List<StatisticsFor>): StatisticGraphData {
        val max = list.maxBy { it.data?.distance ?: 0f }.data?.distance ?: 5f
        return StatisticGraphData(
            scale = distanceScale(max),
            items = list.map { GraphData(it.periodType.value.toString(),
                ((it.data?.distance ?: 0f) / max)
            ) }.toImmutableList()
        )
    }

    private fun distanceScale(distance:Float):ImmutableList<String>{
        val km = distance / 1000f
        if (km >= 1f){
            return calculateGraphSteps(km.toDouble(), type = "km")
        }
        return calculateGraphSteps(distance.toDouble(), type = "m")
    }

    private fun maxDuration(duration: Long): ImmutableList<String> {
        val hours = duration / 3600000 // Convert duration to hours (1 hour = 3600000 milliseconds)
        if (hours > 0) {
            return calculateGraphSteps(hours.toDouble(), type = "h")
        }

        val minutes = duration / 60000 // Convert duration to minutes (1 minute = 60000 milliseconds)
        if (minutes > 0) {
            return calculateGraphSteps(minutes.toDouble(), type = "m")
        }

        val seconds = duration / 1000 // Convert duration to seconds (1 second = 1000 milliseconds)
        if (seconds > 0) {
            return calculateGraphSteps(seconds.toDouble(), type = "s")
        }

        return calculateGraphSteps(duration.toDouble(), type = "millis")
    }
}

private fun calculateGraphSteps(maxValue: Double, size:Int = 5, type:String = ""): ImmutableList<String> {
    if (size < 2) throw Exception("size can't be less then 2")
    if (maxValue <= 0.0) return List(size){ it.toString() }.reversed().toImmutableList()
    val stepSize = when {
        maxValue <= 10 -> 1
        maxValue <= 50 -> 5
        maxValue <= 100 -> 10
        maxValue <= 500 -> 25
        maxValue <= 1000 -> 100
        maxValue <= 100000 -> 1000
        else -> {
            throw Exception()
        }
    }

    val maxRangeValue = ((maxValue + stepSize - 1) / stepSize) * stepSize
    return List(size){ it * maxRangeValue / (size -1) }
        .toSet()
        .map {
            val v = if (it < 1000){
                it.roundToString()
            }else{
                "${(it / 1000f).roundToString(2)}k"
            }
            type.takeIf { it.isNotBlank() }?.let { "$v $it" } ?: v
        }.reversed().toImmutableList()
}

enum class StatisticDataType {
    Steps, Duration, Calories, Distance;
}

data class StatisticGraphData(
    val scale:ImmutableList<String> = calculateGraphSteps(0.0),
    val items:ImmutableList<GraphData> = LocalDateTime.Companion.getCurrentWeekDays()
        .map {
            GraphData(it.dayOfMonth.toString(), 0f)
        }.toImmutableList()
)

data class GraphData(
    val barName:String,
    val barProgress:Float
)