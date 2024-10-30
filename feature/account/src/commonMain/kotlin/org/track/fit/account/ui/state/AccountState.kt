package org.track.fit.account.ui.state

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.track.fit.common.actions.CommonAction
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.common.ui.strings.toUiText
import org.track.fit.domain.usecase.LogOutUC
import org.track.fit.services.service.PedometerServiceManager
import org.track.fit.ui.util.actions.UIActions

@Stable
interface AccountState:AccountSettingsState {

    val uiActions: UIActions

    fun logout()
}

class AccountStateImpl(
    private val logOutUC: LogOutUC,
    private val scope:CoroutineScope,
    private val pedometerManager: PedometerServiceManager,
    override val uiActions: UIActions,
    accountSettingsState:AccountSettingsState
):AccountState,
    AccountSettingsState by accountSettingsState{

    override fun logout() {
        scope.launch {
            pedometerManager.stop()
            val res = logOutUC.invoke()
            when(res){
                is Result.Error -> {
                    if (res.error == AuthError.UnknownError){
                        uiActions.sendAction(CommonAction.ShowSnackBar(res.error.toUiText()))
                    }
                }
                is Result.Success -> {
                    uiActions.sendAction(res.data)
                }
            }
        }
    }
}