package org.track.fit.registration.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import org.track.fit.data.repository.preferences.PersonalPreferences

@Stable
interface StepsGoalState:Saveable {

    val items:List<Int>

    val currentPosition: StateFlow<Int>

    fun setCurrentPosition(index:Int)
}

class StepsGoalStateImpl(
    private val savedStateHandle: SavedStateHandle,
    private val onSave:(PersonalPreferences, String)->Unit
):StepsGoalState{

    override val step: RegistrationStep = RegistrationStep.YourGoal

    override val items: List<Int> = (500 .. 50000 step 500).toList()

    override val currentPosition: StateFlow<Int> = savedStateHandle.getStateFlow(SELECTED_POSITION, 11)

    override fun setCurrentPosition(index: Int) {
        savedStateHandle[SELECTED_POSITION] = index
    }

    override fun save() {
        val goal = items.getOrNull(currentPosition.value) ?: return
        onSave(PersonalPreferences.StepsGoal, goal.toString())
    }
}

private const val SELECTED_POSITION = "StepsGoalState.SELECTED_POSITION"