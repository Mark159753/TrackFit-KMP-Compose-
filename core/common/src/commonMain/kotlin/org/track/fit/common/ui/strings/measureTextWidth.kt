package org.track.fit.common.ui.strings

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.DpSize

@Composable
fun measureText(text: String, style: TextStyle): DpSize {
    val textMeasurer = rememberTextMeasurer()
    val size = textMeasurer.measure(text, style).size
    return with(LocalDensity.current) { DpSize(width = size.width.toDp(), height = size.height.toDp()) }
}