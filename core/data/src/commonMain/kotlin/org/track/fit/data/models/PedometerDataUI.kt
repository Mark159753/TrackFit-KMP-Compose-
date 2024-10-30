package org.track.fit.data.models

import org.track.fit.common.ui.strings.UIText

data class PedometerDataUI(
    val steps:Int,
    val kcal:String,
    val distance:String,
    val duration: UIText
)
