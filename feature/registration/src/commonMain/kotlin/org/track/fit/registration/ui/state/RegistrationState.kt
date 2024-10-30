package org.track.fit.registration.ui.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.track.fit.common.actions.NavAction
import org.track.fit.common.parcelable.CommonParcelable
import org.track.fit.common.parcelable.CommonParcelize
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository
import org.track.fit.ui.util.actions.UIActions

@Stable
interface RegistrationState {

    val stepInfo:StateFlow<StepInfo>

    val genderState:GenderState
    val ageState:AgeState
    val heightState:HeightState
    val goalState:StepsGoalState
    val weightState: WeightState

    fun onContinue()

    fun onSkip()
}

@Immutable
@CommonParcelize
data class StepInfo(
    val current:RegistrationStep = RegistrationStep.YourGender,
    val position:Int = 0,
    val haveNext:Boolean = true,
    val stepsCount:Int,
):CommonParcelable

class RegistrationStateImpl(
    private val savedStateHandle: SavedStateHandle,
    private val scope:CoroutineScope,
    private val uiActions: UIActions,
    private val personalUserPreferencesRepository: PersonalPreferencesRepository
):RegistrationState{

    private val steps = RegistrationStep.entries

    override val genderState:GenderState = GenderStateImpl(savedStateHandle, this::onSave)
    override val ageState:AgeState = AgeStateImpl(savedStateHandle, this::onSave)
    override val heightState:HeightState = HeightStateImpl(savedStateHandle, this::onSave)
    override val goalState:StepsGoalState = StepsGoalStateImpl(savedStateHandle, this::onSave)
    override val weightState: WeightState = WeightStateImpl(savedStateHandle, this::onSave)

    override val stepInfo: StateFlow<StepInfo> = savedStateHandle
        .getStateFlow(SAVED_STEP_INFO, 0)
        .map { index ->
            val step = steps[index]
            StepInfo(
                current = step,
                position = index,
                haveNext = index < (steps.size - 1),
                stepsCount = steps.size
            )
        }
        .stateIn(
            scope = scope,
            initialValue = StepInfo(stepsCount = steps.size),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    override fun onContinue() {
        saveStep(stepInfo.value.current)
        if (stepInfo.value.haveNext){
            toNextStep()
        }else{
            scope.launch {
                uiActions.sendAction(NavAction.NavToHome)
            }
        }
    }

    override fun onSkip() {
        if (stepInfo.value.haveNext){
            toNextStep()
        }
    }

    private fun toNextStep(){
        val nextIndex = stepInfo.value.position + 1
        if (nextIndex >= steps.size) return
        savedStateHandle[SAVED_STEP_INFO] = nextIndex
    }

    private fun saveStep(step:RegistrationStep){
        saveStep(
            step,
            genderState,
            ageState,
            heightState,
            goalState,
            weightState
        )
    }

    private fun saveStep(step:RegistrationStep, vararg saveables: Saveable){
        val saveable = saveables.firstOrNull { it.step == step } ?: return
        saveable.save()
    }

    private fun onSave(field: PersonalPreferences, value:String){
        scope.launch {
            personalUserPreferencesRepository.savePreference(field, value)
        }
    }

    companion object{
        private const val SAVED_STEP_INFO = "org.track.fit.registration.SAVED_STEP_INFO"
    }
}