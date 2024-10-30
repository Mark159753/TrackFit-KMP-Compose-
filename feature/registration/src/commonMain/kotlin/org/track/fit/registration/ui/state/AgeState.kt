package org.track.fit.registration.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import org.track.fit.data.repository.preferences.PersonalPreferences

@Stable
interface AgeState:Saveable{

    val currentPosition:StateFlow<Int>

    val items:List<Int>

    fun setCurrentPosition(index:Int)

}

class AgeStateImpl(
    private val savedStateHandle: SavedStateHandle,
    private val onSave:(PersonalPreferences, String)->Unit
):AgeState{

    override val items: List<Int> = (8 .. 110).toList()

    override val step: RegistrationStep = RegistrationStep.YourAge

    override val currentPosition: StateFlow<Int> = savedStateHandle
        .getStateFlow(SELECTED_POSITION, 10)

    override fun setCurrentPosition(index: Int) {
        savedStateHandle[SELECTED_POSITION] = index
    }

    override fun save() {
        val age = items.getOrNull(currentPosition.value) ?: return
        onSave(PersonalPreferences.Age, age.toString())
    }
}

private const val SELECTED_POSITION = "AgeState.SELECTED_POSITION"