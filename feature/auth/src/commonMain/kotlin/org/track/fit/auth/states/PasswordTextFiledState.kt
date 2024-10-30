package org.track.fit.auth.states

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import org.track.fit.domain.validation.base.IValidator
import org.track.fit.domain.validation.base.firstErrorOrNull
import org.track.fit.common.ui.strings.UIText

@Stable
interface PasswordTextFiledState:AuthTextFiledState, IsCorrect {

    var displayPassword:Boolean

    fun toggleDisplayPassword()

}

class PasswordTextFiledStateImpl(
    scope:CoroutineScope,
    private val validators:List<IValidator>
):PasswordTextFiledState,
    AuthTextFiledState by AuthTextFiledStateImpl(scope){

    override var displayPassword: Boolean by mutableStateOf(false)

    override fun toggleDisplayPassword() {
        displayPassword = displayPassword.not()
    }

    override fun isCorrect(): Boolean {
        val msg = validators.firstErrorOrNull(value)
        onSetErrorMsg(msg?.let { UIText.ResString(it) })
        return msg == null
    }
}