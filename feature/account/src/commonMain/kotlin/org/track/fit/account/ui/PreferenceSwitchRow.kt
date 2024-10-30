package org.track.fit.account.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.track.fit.ui.theme.localColors

@Composable
fun PreferenceSwitchRow(
    modifier: Modifier = Modifier,
    isSelected:Boolean,
    onCheckedChange:(Boolean)->Unit = {},
    text:String
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp),
            color = MaterialTheme.localColors.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = isSelected,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.localColors.primary,
                uncheckedTrackColor = MaterialTheme.localColors.outline,
            )
        )
    }
}