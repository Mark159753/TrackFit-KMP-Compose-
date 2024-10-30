package org.track.fit.auth.signUp

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.auth.states.AuthTextFiledState
import org.track.fit.auth.states.PasswordTextFiledState
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.email_ic
import trackfit.core.common.generated.resources.lock_ic
import trackfit.core.common.generated.resources.visibility_ic
import trackfit.core.common.generated.resources.visibility_off_ic
import trackfit.feature.auth.generated.resources.Res
import trackfit.core.common.generated.resources.Res as SharedRes
import trackfit.feature.auth.generated.resources.sign_up_email
import trackfit.feature.auth.generated.resources.sign_up_password

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    state:AuthTextFiledState
){

    val isValid by state.isValid.collectAsStateWithLifecycle()

    AuthTextField(
        modifier = modifier,
        state = state,
        title = stringResource(Res.string.sign_up_email),
        leadingIcon = {
            Icon(
                painter = painterResource(SharedRes.drawable.email_ic),
                contentDescription = "email_ic",
                modifier = Modifier
                    .size(20.dp),
                tint = if (isValid) MaterialTheme.localColors.onSurface else MaterialTheme.localColors.error
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            autoCorrect = false,
            imeAction = ImeAction.Done
        ),
    )
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    state:PasswordTextFiledState
){
    val isValid by state.isValid.collectAsStateWithLifecycle()

    AuthTextField(
        modifier = modifier,
        state = state,
        title = stringResource(Res.string.sign_up_password),
        visualTransformation = if (state.displayPassword) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                painter = painterResource(SharedRes.drawable.lock_ic),
                contentDescription = "lock_ic",
                modifier = Modifier
                    .size(20.dp),
                tint = if (isValid) MaterialTheme.localColors.onSurface else MaterialTheme.localColors.error
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            autoCorrect = false,
            imeAction = ImeAction.Done
        ),
        trailing = {
            IconButton(
                onClick = { state.toggleDisplayPassword() }
            ){
                if (state.displayPassword) {
                    Icon(
                        painter = painterResource(SharedRes.drawable.visibility_ic),
                        contentDescription = "visibility_ic",
                        modifier = Modifier
                            .size(20.dp),
                        tint = MaterialTheme.localColors.onSurface
                    )
                }else{
                    Icon(
                        painter = painterResource(SharedRes.drawable.visibility_off_ic),
                        contentDescription = "visibility_off_ic",
                        modifier = Modifier
                            .size(20.dp),
                        tint = MaterialTheme.localColors.onSurface
                    )
                }
            }
        }
    )
}