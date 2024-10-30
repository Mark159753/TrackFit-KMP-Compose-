package org.track.fit.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.track.fit.ui.theme.localColors

@Composable
fun FullScreenLoading(
    isShowingDialog: Boolean
){
    if (isShowingDialog){
        Dialog(
            onDismissRequest = { },
            DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ){
            Content()
        }
    }
}

@Composable
private fun Content(){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            strokeWidth = 4.dp,
            color = MaterialTheme.localColors.tertiary,
            strokeCap = StrokeCap.Round
        )
    }
}