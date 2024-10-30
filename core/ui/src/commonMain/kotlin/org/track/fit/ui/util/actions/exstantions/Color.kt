package org.track.fit.ui.util.actions.exstantions

import androidx.compose.ui.graphics.Color

fun Color.darken(factor: Float): Color {
    return this.copy(
        red = this.red * factor,
        green = this.green * factor,
        blue = this.blue * factor
    )
}