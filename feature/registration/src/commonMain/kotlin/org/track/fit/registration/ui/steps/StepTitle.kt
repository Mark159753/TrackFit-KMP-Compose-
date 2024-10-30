package org.track.fit.registration.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.track.fit.ui.theme.localColors

@Composable
fun StepTitle(
    modifier: Modifier = Modifier,
    title:AnnotatedString,
    subtitle:String,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.localColors.onSecondaryContainer,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.localColors.outline,
            textAlign = TextAlign.Center
        )

    }
}