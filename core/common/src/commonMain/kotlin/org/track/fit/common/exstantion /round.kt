package org.track.fit.common.exstantion

import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

fun Double.roundToString(power:Int = 2):String{
    return if (power <= 0)
        this.roundToInt().toString()
    else (round(this * 100) / 10.0.pow(power).toInt()).toString()
}

fun Float.roundToString(power:Int = 2):String{
    val roundedValue = if (power <= 0) this.roundToInt().toFloat() else (round(this * 10.0.pow(power)) / 10.0.pow(power)).toFloat()
    return if (roundedValue % 1.0 == 0.0) roundedValue.toInt().toString()
    else roundedValue.toString()
}

fun Float.fromMetersToKm(power:Int = 2):String{
    val km = this / 1000
    return if (power <= 0)
        this.roundToInt().toString()
    else (round(km * 100) / 10.0.pow(power)).toString()
}