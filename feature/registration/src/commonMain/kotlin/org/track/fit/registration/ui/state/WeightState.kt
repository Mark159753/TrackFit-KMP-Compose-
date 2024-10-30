package org.track.fit.registration.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import org.track.fit.data.repository.preferences.PersonalPreferences

@Stable
interface WeightState:Saveable {

    val items:List<Int>

    val currentPosition: StateFlow<Int>

    fun setCurrentPosition(index:Int)
}

class WeightStateImpl(
    private val savedStateHandle: SavedStateHandle,
    private val onSave:(PersonalPreferences, String)->Unit
):WeightState{

    override val step: RegistrationStep = RegistrationStep.YourWeight
    override val items: List<Int> = (40 .. 160).toList()

    override val currentPosition: StateFlow<Int> = savedStateHandle.getStateFlow(SELECTED_POSITION, 14)

    override fun setCurrentPosition(index: Int) {
        savedStateHandle[SELECTED_POSITION] = index
    }

    override fun save() {
        val weight = items.getOrNull(currentPosition.value) ?: return
        onSave(PersonalPreferences.Weight, weight.toString())
    }
}

private const val SELECTED_POSITION = "WeightState.SELECTED_POSITION"