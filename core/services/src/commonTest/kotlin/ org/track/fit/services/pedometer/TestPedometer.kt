package  org.track.fit.services.pedometer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import org.track.fit.data.models.DailyAchievements
import org.track.fit.domain.usecase.CalcCaloriesUC
import org.track.fit.domain.usecase.CalcDistanceUC
import org.track.fit.domain.usecase.CalcSpeedUC
import org.track.fit.domain.usecase.CalcStepLengthUC
import org.track.fit.domain.usecase.GetStepLengthUC
import org.track.fit.services.pedometer.dummy.TestPersonalPreferencesRepository
import org.track.fit.services.pedometer.dummy.TestPreferences
import org.track.fit.services.pedometer.dummy.TestStatisticsRepository
import org.track.fit.services.pedometer.dummy.TestStepCounter
import org.track.fit.services.service.pedometer.PedometerImpl
import kotlin.test.Test
import kotlin.test.assertEquals


class TestPedometer{

    private fun createPedometer(
        steps:Int = 0,
        startDate:LocalDateTime? = null,
        achievements: DailyAchievements? = null
    ) = PedometerImpl(
        stepCounter = TestStepCounter(MutableStateFlow(steps)),
        appPreferences = TestPreferences(startDate),
        repository = TestStatisticsRepository(achievements),
        calcCaloriesUC = CalcCaloriesUC(),
        calcDistanceUC = CalcDistanceUC(),
        calcSpeedUC = CalcSpeedUC(),
        getStepLengthUC = GetStepLengthUC(
            preferences = TestPersonalPreferencesRepository(),
            calcStepLengthUC = CalcStepLengthUC()
        ),
        appUserPreferences = TestPersonalPreferencesRepository()
    )

    @Test
    fun initStepCounter() = runBlocking {
        val pedometer = createPedometer(
            steps = 5
        )

        val result = pedometer.data.first()

        assertEquals(5, result.steps)
    }

    @Test
    fun step10withSaved10() = runBlocking {
        val pedometer = createPedometer(
            steps = 10,
            achievements = DailyAchievements(
                steps = 10,
                kcal = 0.2,
                distance = 70f,
                duration = 12
            )
        )

        val result = pedometer.data.first()

        assertEquals(10, result.steps)
    }

    @Test
    fun step10withSaved9() = runBlocking {
        val pedometer = createPedometer(
            steps = 10,
            achievements = DailyAchievements(
                steps = 9,
                kcal = 0.2,
                distance = 70f,
                duration = 12
            )
        )

        val result = pedometer.data.first()

        assertEquals(10, result.steps)
    }

    @Test
    fun step10withSaved7() = runBlocking {
        val pedometer = createPedometer(
            steps = 10,
            achievements = DailyAchievements(
                steps = 7,
                kcal = 0.2,
                distance = 70f,
                duration = 12
            )
        )

        val result = pedometer.data.first()

        assertEquals(10, result.steps)
    }

    @Test
    fun step9withSaved10() = runBlocking {
        val pedometer = createPedometer(
            steps = 9,
            achievements = DailyAchievements(
                steps = 10,
                kcal = 0.2,
                distance = 70f,
                duration = 12
            )
        )

        val result = pedometer.data.first()

        assertEquals(19, result.steps)
    }

}



