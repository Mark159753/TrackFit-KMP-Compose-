package org.track.fit.registration.ui.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.track.fit.common.constants.Gender
import org.track.fit.data.repository.preferences.PersonalPreferences
import trackfit.core.common.generated.resources.fit_man
import trackfit.core.common.generated.resources.fit_woman
import trackfit.feature.registration.generated.resources.Res
import trackfit.feature.registration.generated.resources.registration_gender_man
import trackfit.feature.registration.generated.resources.registration_gender_woman
import trackfit.core.common.generated.resources.Res as SharedRes

@Stable
interface GenderState:Saveable {

    val items:List<GenderItem>

    val selectedPosition:StateFlow<Int>

    fun setCurrentPosition(index:Int)

}

class GenderStateImpl(
    private val savedStateHandle: SavedStateHandle,
    private val onSave:(PersonalPreferences, String)->Unit
):GenderState{

    override val step: RegistrationStep = RegistrationStep.YourGender

    override val selectedPosition: StateFlow<Int> = savedStateHandle.getStateFlow(SELECTED_POSITION, 0)

    override val items: List<GenderItem> = listOf(
        GenderItem(
            img = SharedRes.drawable.fit_man,
            title = Res.string.registration_gender_man,
            gender = Gender.Male
        ),
        GenderItem(
            img = SharedRes.drawable.fit_woman,
            title = Res.string.registration_gender_woman,
            gender = Gender.Female
        ),
    )

    override fun setCurrentPosition(index: Int) {
        savedStateHandle[SELECTED_POSITION] = index
    }

    override fun save() {
        val gender = items.getOrNull(selectedPosition.value)?.gender ?: return
        onSave(PersonalPreferences.Gender, gender.name)
    }
}

private const val SELECTED_POSITION = "GenderState.SELECTED_POSITION"

@Immutable
data class GenderItem(
    val img:DrawableResource,
    val title:StringResource,
    val gender:Gender
)