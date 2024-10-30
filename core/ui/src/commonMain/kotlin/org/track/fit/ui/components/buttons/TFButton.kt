package org.track.fit.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.track.fit.ui.theme.localColors

@Composable
fun TFButton(
    modifier: Modifier = Modifier,
    title:String,
    onClick:()->Unit,
    leadingIcon:(@Composable ()->Unit)? = null,
    trailingIcon:(@Composable ()->Unit)? = null,
    colors:TFButtonColors = defaultButtonColors()
){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(color = colors.container)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 12.dp,
                vertical = 13.dp
            ),
    ){
        leadingIcon?.let {
            Box(
                modifier = Modifier.align(Alignment.CenterStart)
            ) { leadingIcon.invoke() }
        }

        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.button.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 4.dp),
            color = colors.content
        )

        trailingIcon?.let {
            Box(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) { trailingIcon.invoke() }
        }
    }
}

@Composable
fun defaultButtonColors(
    container: Color = MaterialTheme.localColors.primary,
    content: Color = MaterialTheme.localColors.onPrimary
) = TFButtonColors(
    container = container,
    content = content
)

data class TFButtonColors(
    val container:Color,
    val content:Color
)