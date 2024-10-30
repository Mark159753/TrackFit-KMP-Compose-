package org.track.fit.track.ui.state

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.track.fit.common.ui.strings.UIText
import org.track.fit.data.mappers.toPedometerDataUi
import org.track.fit.data.models.PedometerDataUI
import org.track.fit.data.repository.statistics.StatisticsRepository
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.duration_format

@Stable
interface PedometerState {

    val pedometerData: StateFlow<PedometerDataUI>
}

class PedometerStateImpl(
    statisticsRepository: StatisticsRepository,
    scope: CoroutineScope
):PedometerState{

    private val _pedometerData = statisticsRepository
        .todayAchievements
        .filterNotNull()
        .map { item ->
            item.toPedometerDataUi()
        }
        .stateIn(
            scope = scope,
            initialValue = PedometerDataUI(
                steps = 0,
                kcal = "0.0",
                distance = "0",
                duration = UIText.ResString(Res.string.duration_format, arrayOf("0", "0"))
            ),
            started = SharingStarted.Eagerly
        )
    override val pedometerData: StateFlow<PedometerDataUI>
        get() = _pedometerData
}