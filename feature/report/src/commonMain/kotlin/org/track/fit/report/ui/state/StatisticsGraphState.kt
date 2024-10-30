package org.track.fit.report.ui.state

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import org.track.fit.domain.usecase.statistics.GetStatisticsByDataType
import org.track.fit.domain.usecase.statistics.GetStatisticsFor
import org.track.fit.domain.usecase.statistics.Period
import org.track.fit.domain.usecase.statistics.StatisticDataType
import org.track.fit.domain.usecase.statistics.StatisticGraphData

@Stable
interface StatisticsGraphState {

    val statisticPeriod:StateFlow<Period>

    val statisticDataType:StateFlow<StatisticDataType>

    val statistics:StateFlow<StatisticGraphData>

    fun changeStatisticPeriod(period: Period)

    fun changeStatisticDataType(type: StatisticDataType)

}

class StatisticsGraphStateImpl(
    getStatisticsFor: GetStatisticsFor,
    getStatisticsByDataType: GetStatisticsByDataType,
    scope: CoroutineScope
):StatisticsGraphState{

    private val _statisticPeriod = MutableStateFlow(Period.ThisWeek)
    override val statisticPeriod: StateFlow<Period>
        get() = _statisticPeriod

    private val _statisticDataType = MutableStateFlow(StatisticDataType.Steps)
    override val statisticDataType: StateFlow<StatisticDataType>
        get() = _statisticDataType

    @OptIn(ExperimentalCoroutinesApi::class)
    override val statistics: StateFlow<StatisticGraphData> = statisticPeriod
        .flatMapLatest { period ->
            getStatisticsFor(period)
        }.combine(statisticDataType){ items, dataType ->
            getStatisticsByDataType(items, dataType)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = scope,
            initialValue = StatisticGraphData(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    override fun changeStatisticPeriod(period: Period) {
        _statisticPeriod.value = period
    }

    override fun changeStatisticDataType(type: StatisticDataType) {
        _statisticDataType.value = type
    }

}
