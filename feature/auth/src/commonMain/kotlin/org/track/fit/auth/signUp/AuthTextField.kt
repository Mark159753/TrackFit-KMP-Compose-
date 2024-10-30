package org.track.fit.auth.signUp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.track.fit.auth.states.AuthTextFiledState
import org.track.fit.ui.components.text_field.TFTextField
import org.track.fit.ui.theme.localColors

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    state: AuthTextFiledState,
    title:String,
    leadingIcon: @Composable ()->Unit,
    keyboardOptions: KeyboardOptions,
    trailing: (@Composable ()->Unit)?  = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
){
    val isValid by state.isValid.collectAsStateWithLifecycle()
    val errorMsg by state.errorMsg.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.localColors.onBackground
        )

        Spacer(Modifier.height(4.dp))

        TFTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.value,
            onValueChanged = state::onValueChanged,
            placeholder = title,
            isError = !isValid,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            trailing = trailing,
            visualTransformation = visualTransformation
        )

        AnimatedVisibility(visible = !isValid){
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(Modifier.height(4.dp))

                Text(
                    text = errorMsg?.asString() ?: "",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.localColors.error,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}