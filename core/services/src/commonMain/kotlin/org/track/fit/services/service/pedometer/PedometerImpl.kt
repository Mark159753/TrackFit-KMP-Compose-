package org.track.fit.services.service.pedometer

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.LocalDateTime
import org.track.fit.common.constants.DefaultBodyWeight
import org.track.fit.common.date.now
import org.track.fit.common.date.toMilliseconds
import org.track.fit.data.models.DailyAchievements
import org.track.fit.data.models.PedometerData
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.data.repository.statistics.StatisticsRepository
import org.track.fit.domain.usecase.CalcCaloriesUC
import org.track.fit.domain.usecase.CalcDistanceUC
import org.track.fit.domain.usecase.CalcSpeedUC
import org.track.fit.domain.usecase.GetStepLengthUC
import org.track.fit.local.preferences.AppPreferences
import org.track.fit.services.service.StepCounter

private const val TAG = "Pedometer"

class PedometerImpl(
    private val stepCounter: StepCounter,
    private val appPreferences: AppPreferences,
    private val repository: StatisticsRepository,
    private val calcCaloriesUC: CalcCaloriesUC,
    private val calcDistanceUC: CalcDistanceUC,
    private val calcSpeedUC: CalcSpeedUC,
    private val getStepLengthUC: GetStepLengthUC,
    private val appUserPreferences: PersonalPreferencesRepository,
):Pedometer {

    override val data: Flow<PedometerData>
        get() = createDataFlow()

    private var lastStepTime: Long? = null

    private fun createDataFlow() = combine(
        stepWithAchievementsFlow(),
        getStepLengthFlow(),
    ){ stepWithAchievements, stepLength ->

        calculatePedometerData(
            stepWithAchievements.step,
            stepLength,
            stepWithAchievements.achievements
        )
    }

    private fun stepWithAchievementsFlow() = stepCounter
        .steps
        .onStart {
            appPreferences.setStartTime(LocalDateTime.now())
        }
        .map { steps ->
            val achievements = repository.todayAchievements.first()
            val passedSteps = (achievements?.steps ?: 0)

            StepWithAchievements(
                step = if (passedSteps <= steps){
                    steps - passedSteps
                }else steps,
                achievements = achievements
            )
        }

    private fun getStepLengthFlow() = flow {
        emit(getStepLengthUC())
    }

    private suspend fun calculatePedometerData(
        step:Int,
        stepLength:Float,
        achievements:DailyAchievements?
    ): PedometerData {

        val weight = appUserPreferences.getPreference(PersonalPreferences.Weight)?.toInt() ?: DefaultBodyWeight

        val stepTime = LocalDateTime.now().toMilliseconds()
        val distance = calcDistanceUC(stepLength, step)
        val calories = lastStepTime?.let {
            val speed = calcSpeedUC(distance, it, stepTime)
            calcCaloriesUC(speed, weight, stepTime - it)
        } ?: 0.0

        val stepDuration = lastStepTime?.let { time -> stepTime - time } ?: 0L

        lastStepTime = stepTime

        return PedometerData(
            steps = step + (achievements?.steps ?: 0),
            kcal = calories + (achievements?.kcal ?: 0.0),
            distance = distance + (achievements?.distance ?: 0f),
            duration = stepDuration + (achievements?.duration ?: 0L)
        )
    }

}

data class StepWithAchievements(
    val step:Int,
    val achievements:DailyAchievements?
)