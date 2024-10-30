package org.track.fit.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jetbrains.compose.resources.stringResource
import org.track.fit.common.constants.AppLanguage
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.dialog_language_title

@Composable
fun ChangeLanguageDialog(
    current:AppLanguage = AppLanguage.EN,
    onSelect:(AppLanguage)->Unit = {},
    onDismiss:()->Unit = {}
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ){
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(20.dp),
            backgroundColor = MaterialTheme.localColors.surfaceVariant
        ){
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.dialog_language_title),
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.localColors.onSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(24.dp))

                AppLanguage.entries.forEach { item ->
                    LanguageItem(
                        selected = current == item,
                        onSelect = onSelect,
                        item = item
                    )
                }

            }
        }
    }
}

@Composable
private fun LanguageItem(
    modifier: Modifier = Modifier,
    selected:Boolean,
    item: AppLanguage,
    onSelect:(AppLanguage)->Unit
){
    Text(
        text = stringResource(item.displayName),
        style = MaterialTheme.typography.body1,
        color = if (selected) MaterialTheme.localColors.tertiary else MaterialTheme.localColors.onSurfaceVariant,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect(item) }
            .padding(vertical = 10.dp, horizontal = 14.dp)
    )
}