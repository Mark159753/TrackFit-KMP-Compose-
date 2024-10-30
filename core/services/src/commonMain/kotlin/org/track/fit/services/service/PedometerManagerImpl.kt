package org.track.fit.services.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import org.track.fit.data.mappers.toDailyAchievements
import org.track.fit.data.repository.statistics.StatisticsRepository
import org.track.fit.services.service.pedometer.Pedometer

class PedometerManagerImpl(
    private val pedometer: Pedometer,
    private val statisticsRepository: StatisticsRepository,
):PedometerManager {

    private val _state = MutableStateFlow<PedometerState>(PedometerState.Stop)
    override val state: StateFlow<PedometerState>
        get() = _state

    override suspend fun startObserving() {
        pedometer.data
            .onCompletion { e ->
                _state.value = when(e){
                    is StepCounterPermissionRequired -> PedometerState.Error(PedometerError.PermissionDenied())
                    is StepCounterNotSupport ->  PedometerState.Error(PedometerError.NotSupported())
                    else -> PedometerState.Stop
                }
            }
            .collectLatest { data ->
                _state.value = PedometerState.Running
                statisticsRepository.saveTodayAchievements(data.toDailyAchievements())
            }
    }
}