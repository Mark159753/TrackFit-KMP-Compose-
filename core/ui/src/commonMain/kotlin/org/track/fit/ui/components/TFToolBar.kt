package org.track.fit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.footprint_ic

@Composable
fun TFToolBar(
    modifier: Modifier = Modifier,
    title:String,
    trailing: (@Composable ()-> Unit)? = null,
    leading: @Composable ()-> Unit = {
        Icon(
            painter = painterResource(Res.drawable.footprint_ic),
            contentDescription = "logo",
            tint = MaterialTheme.localColors.primary,
            modifier = Modifier.size(22.dp)
        )
    },
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp)
){

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .height(54.dp)
    ){
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
        ){
            leading()
        }

        Text(
            text = title,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.localColors.onSurface,
            modifier = Modifier
                .align(Alignment.Center)
        )


        trailing?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ){
                it.invoke()
            }
        }
    }

}