package org.track.fit.registration.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import org.track.fit.data.repository.preferences.PersonalPreferences

@Stable
interface HeightState:Saveable{

    val items:List<Int>

    val currentPosition: StateFlow<Int>

    fun setCurrentPosition(index:Int)

}

class HeightStateImpl(
    private val savedStateHandle: SavedStateHandle,
    private val onSave:(PersonalPreferences, String)->Unit
):HeightState{

    override val items: List<Int> = (120 .. 230).toList()

    override val step: RegistrationStep = RegistrationStep.YourHeight

    override val currentPosition: StateFlow<Int> = savedStateHandle.getStateFlow(SELECTED_POSITION, 40)

    override fun setCurrentPosition(index: Int) {
        savedStateHandle[SELECTED_POSITION] = index
    }

    override fun save() {
        val height = items.getOrNull(currentPosition.value) ?: return
        onSave(PersonalPreferences.Height, height.toString())
    }
}

private const val SELECTED_POSITION = "HeightState.SELECTED_POSITION"