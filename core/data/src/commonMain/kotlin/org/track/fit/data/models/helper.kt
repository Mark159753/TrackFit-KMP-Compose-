package org.track.fit.data.models

internal fun<T:Any> T.toIntOrZero() = when(this){
    is Long -> (this as Long).toInt()
    is Double -> (this as Double).toInt()
    else -> 0
}

internal fun <T:Any> T.toDoubleOrZero() = when(this){
    is Long -> (this as Long).toDouble()
    is Double -> this
    else -> 0.0
}

internal fun <T:Any> T.toFloatOrZero() = when(this){
    is Long -> (this as Long).toFloat()
    is Double -> (this as Double).toFloat()
    else -> 0f
}

internal fun <T:Any> T.toLongOrZero() = when(this){
    is Long -> this
    is Double -> (this as Double).toLong()
    else -> 0L
}