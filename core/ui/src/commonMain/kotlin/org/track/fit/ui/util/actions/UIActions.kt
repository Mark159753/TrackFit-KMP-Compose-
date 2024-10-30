package org.track.fit.ui.util.actions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.track.fit.common.actions.Action
import org.track.fit.common.actions.DisplayErrorDialog

@Stable
interface UIActions {

    val actions: Flow<Action>

    val errorDialog: MutableState<DisplayErrorDialog?>

    fun hideErrorDialog()

    suspend fun sendAction(action: Action)
}

class UIActionsImpl:UIActions{

    private val _actions = Channel<Action>()
    override val actions: Flow<Action>
        get() = _actions.receiveAsFlow()

    override val errorDialog: MutableState<DisplayErrorDialog?> = mutableStateOf(null)

    override suspend fun sendAction(action: Action) {
        _actions.send(action)
    }

    override fun hideErrorDialog() {
        errorDialog.value = null
    }
}