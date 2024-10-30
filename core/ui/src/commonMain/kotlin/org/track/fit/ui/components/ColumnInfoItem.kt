package org.track.fit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.track.fit.ui.theme.localColors

@Composable
fun ColumnInfoItem(
    modifier: Modifier = Modifier,
    colors:ColumnInfoColors = columnInfoDefaults(),
    icon:DrawableResource,
    title:String,
    value:String,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = colors.iconTint,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            color = colors.value
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.body2,
            color = colors.title
        )
    }
}

@Composable
fun columnInfoDefaults(
    iconTint:Color = MaterialTheme.localColors.timeIcon,
    title:Color = MaterialTheme.localColors.outline,
    value:Color = MaterialTheme.localColors.onSurface
) = ColumnInfoColors(
    iconTint = iconTint,
    title = title,
    value = value
)

@Immutable
data class ColumnInfoColors(
    val iconTint:Color,
    val title:Color,
    val value:Color
)