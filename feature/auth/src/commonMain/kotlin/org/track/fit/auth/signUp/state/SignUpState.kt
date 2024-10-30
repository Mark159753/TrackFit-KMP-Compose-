package org.track.fit.auth.signUp.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.track.fit.auth.states.AuthTextFiledState
import org.track.fit.auth.states.EmailTextFiledState
import org.track.fit.auth.states.IsAllCorrectState
import org.track.fit.auth.states.PasswordTextFiledState
import org.track.fit.auth.states.PasswordTextFiledStateImpl
import org.track.fit.auth.states.SignInWithGoogleState
import org.track.fit.auth.states.SignInWithGoogleStateImpl
import org.track.fit.common.actions.CommonAction
import org.track.fit.common.result.AuthError
import org.track.fit.common.result.Result
import org.track.fit.common.ui.strings.toUiText
import org.track.fit.domain.usecase.LoginWithGoogleUC
import org.track.fit.domain.usecase.SignUpUC
import org.track.fit.domain.validation.EmailValidator
import org.track.fit.domain.validation.EmptyValidator
import org.track.fit.domain.validation.PasswordValidator
import org.track.fit.ui.util.actions.UIActions

@Stable
interface SignUpState: SignInWithGoogleState {

    var isLoading: Boolean

    val emailState:AuthTextFiledState
    val passwordState:PasswordTextFiledState

    fun onSignUp()
}

class SignUpStateImpl(
    private val scope: CoroutineScope,
    private val signUpUC: SignUpUC,
    private val uiAction: UIActions,
    loginWithGoogleUC: LoginWithGoogleUC,
): SignUpState,
    IsAllCorrectState,
    SignInWithGoogleState by SignInWithGoogleStateImpl(scope, uiAction, loginWithGoogleUC){

    override var isLoading: Boolean by mutableStateOf(false)

    override val emailState = EmailTextFiledState(
        scope = scope,
        validators = listOf(EmptyValidator(), EmailValidator())
    )

    override val passwordState: PasswordTextFiledState = PasswordTextFiledStateImpl(
        scope = scope,
        validators = listOf(EmptyValidator(), PasswordValidator())
    )

    override fun onSignUp() {
        val isAllCorrect = checkIsAllCorrect(emailState, passwordState)
        if (isAllCorrect){
            scope.launch {
                isLoading = true
                when(val result = signUpUC(emailState.value, passwordState.value)){
                    is Result.Error -> {
                        val uiMsg = result.error.toUiText()
                        when(result.error){
                            AuthError.UnknownError -> uiAction
                                .sendAction(CommonAction.ShowSnackBar(msg = uiMsg))
                            AuthError.UserAlreadyExist,
                            AuthError.InvalidEmail -> emailState.onSetErrorMsg(uiMsg)
                            AuthError.WeakPassword,
                            AuthError.InvalidPassword -> passwordState.onSetErrorMsg(uiMsg)
                            else -> {}
                        }
                    }
                    is Result.Success -> {
                        uiAction.sendAction(result.data)
                    }
                }
                isLoading = false
            }
        }
    }
}