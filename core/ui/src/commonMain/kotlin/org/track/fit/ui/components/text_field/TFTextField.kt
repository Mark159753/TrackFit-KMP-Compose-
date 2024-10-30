package org.track.fit.ui.components.text_field

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.track.fit.ui.theme.localColors

@Composable
fun TFTextField(
    modifier: Modifier = Modifier,
    enabled:Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError:Boolean = false,
    placeholder:String? = null,
    label:String? = null,
    maxLines:Int = 1,
    trailing: (@Composable ()->Unit)?  = null,
    leadingIcon: (@Composable ()->Unit)?  = null,
    shape: Shape = RoundedCornerShape(8.dp),
    value:String,
    onValueChanged:(String)->Unit
){
    TextField(
        modifier = modifier
            .then(
                if (isError){
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.localColors.error,
                        shape = shape
                    )
                }else Modifier
            )
            .fillMaxWidth(),
        value = value,
        enabled = enabled,
        interactionSource = interactionSource,
        onValueChange = onValueChanged,
        maxLines = maxLines,
        colors = TextFieldDefaults.textFieldColors(
            textColor = if (isError) MaterialTheme.localColors.error else MaterialTheme.localColors.onBackground,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = MaterialTheme.localColors.surfaceContainer
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
        label = if (!label.isNullOrBlank()) {
            {
                Text(
                    text = label,
                    style = MaterialTheme.typography.overline.copy(fontSize = 12.sp),
                    color = MaterialTheme.localColors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else null,
        shape = shape,
        trailingIcon = trailing,
        visualTransformation = visualTransformation,
        placeholder = placeholder?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.localColors.onSurface
                )
            }
        },
        leadingIcon = leadingIcon,
        isError = isError,

    )
}