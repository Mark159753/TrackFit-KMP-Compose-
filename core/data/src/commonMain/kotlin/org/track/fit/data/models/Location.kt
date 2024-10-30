package org.track.fit.data.models

data class Location(
    val lat:Double,
    val lng:Double
){

    fun toMap() = mapOf(
        "lat" to lat,
        "lng" to lng
    )

    companion object{
        fun fromMap(map:Map<*, *>) = Location(
            lat = map["lat"]!!.toDoubleOrZero(),
            lng = map["lng"]!!.toDoubleOrZero()
        )
    }
}
