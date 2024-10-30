package org.track.fit.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.ui.components.buttons.TFButton
import org.track.fit.ui.components.buttons.defaultButtonColors
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.permission_dialog_cancel_btn
import trackfit.core.common.generated.resources.permission_dialog_grant_btn
import trackfit.core.common.generated.resources.permission_dialog_title

@Composable
fun PermissionRequestDialog(
    onDismiss:()->Unit = {},
    onGrantClick:()->Unit = {},
    isShowingDialog: Boolean = false,
    properties:PermissionDialogProp
){
    if (isShowingDialog) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.localColors.surface)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(Modifier.height(8.dp))

                Box(
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.localColors.primary)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter = painterResource(properties.icon),
                        contentDescription = null,
                        tint = MaterialTheme.localColors.onPrimary,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = stringResource(properties.title),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.localColors.onSurface
                )
                Text(
                    text = stringResource(Res.string.permission_dialog_title),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.localColors.onSurface
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = stringResource(properties.msg),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.localColors.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))

                Buttons(
                    onCancel = onDismiss,
                    onGrant = onGrantClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun Buttons(
    modifier: Modifier = Modifier,
    onGrant:()->Unit,
    onCancel:()->Unit
){
    Column(
        modifier = modifier
    ) {

        TFButton(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(Res.string.permission_dialog_grant_btn),
            onClick = onGrant,
        )

        Spacer(Modifier.height(8.dp))

        TFButton(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(Res.string.permission_dialog_cancel_btn),
            onClick = onCancel,
            colors = defaultButtonColors(
                container = MaterialTheme.localColors.secondaryContainer,
                content = MaterialTheme.localColors.onSecondaryContainer
            )
        )
    }
}

@Immutable
data class PermissionDialogProp(
    val icon:DrawableResource,
    val title:StringResource,
    val msg:StringResource
)