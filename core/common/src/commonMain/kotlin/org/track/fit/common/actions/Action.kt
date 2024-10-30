package org.track.fit.common.actions

import androidx.compose.runtime.Immutable
import org.track.fit.common.ui.strings.UIText

sealed interface Action

sealed interface CommonAction: Action {
    data class ShowSnackBar(val msg:UIText): CommonAction

    data object NavBack: CommonAction

    data class DisplayTopErrorMsg(val msg: UIText):CommonAction
}

sealed interface NavAction: Action {
    data object NavToHome: NavAction
    data object NavToRegistrationSteps: NavAction
    data object NavToAuth: NavAction
}

@Immutable
data class DisplayErrorDialog(
    val msg: UIText,
    val title:UIText? = null
)