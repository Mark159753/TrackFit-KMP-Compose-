package org.track.fit.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.data.repository.statistics.StatisticsRepository
import org.track.fit.home.ui.state.DailyProgressStateImpl
import org.track.fit.home.ui.state.HomeState
import org.track.fit.home.ui.state.HomeStateImpl
import org.track.fit.home.ui.state.PedometerStateImpl
import org.track.fit.ui.util.actions.UIActions
import org.track.fit.ui.util.actions.UIActionsImpl

class HomeViewModel(
    statisticsRepository: StatisticsRepository,
    userPersonalPreferencesRepository: PersonalPreferencesRepository
):ViewModel(),
    UIActions by UIActionsImpl(){

    val state:HomeState = HomeStateImpl(
        pedometerState = PedometerStateImpl(
            scope = viewModelScope,
            statisticsRepository = statisticsRepository,
            userPreferencesRepository = userPersonalPreferencesRepository
            ),
        dailyProgressState = DailyProgressStateImpl(
            statisticsRepository = statisticsRepository,
            personalPreferencesRepository = userPersonalPreferencesRepository,
            scope = viewModelScope
        )
    )
}