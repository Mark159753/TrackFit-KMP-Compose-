package org.track.fit.auth.states

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.track.fit.common.ui.strings.UIText

@Stable
interface AuthTextFiledState {

    var value:String

    val errorMsg:StateFlow<UIText?>

    val isValid:StateFlow<Boolean>

    fun onValueChanged(v:String)

    fun onSetErrorMsg(msg: UIText?)
}

class AuthTextFiledStateImpl(
    scope: CoroutineScope
):AuthTextFiledState{

    override var value: String by mutableStateOf("")

    private val _errorMsg = MutableStateFlow<UIText?>(null)
    override val errorMsg: StateFlow<UIText?>
        get() = _errorMsg

    override val isValid: StateFlow<Boolean> = _errorMsg
        .map { it == null }
        .stateIn(
            scope = scope,
            initialValue = true,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    override fun onValueChanged(v: String) {
        value = v
        onSetErrorMsg(null)
    }

    override fun onSetErrorMsg(msg: UIText?) {
        _errorMsg.value = msg
    }
}