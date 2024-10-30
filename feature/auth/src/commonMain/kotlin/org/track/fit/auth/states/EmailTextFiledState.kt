package org.track.fit.auth.states

import kotlinx.coroutines.CoroutineScope
import org.track.fit.domain.validation.base.IValidator
import org.track.fit.domain.validation.base.firstErrorOrNull
import org.track.fit.common.ui.strings.UIText

class EmailTextFiledState(
    scope: CoroutineScope,
    private val validators:List<IValidator>
):AuthTextFiledState by AuthTextFiledStateImpl(scope), IsCorrect{

    override fun isCorrect(): Boolean {
        val msg = validators.firstErrorOrNull(value)
        onSetErrorMsg(msg?.let { UIText.ResString(it) })
        return msg == null
    }
}