package org.track.fit.data.models


data class DailyAchievements(
    val steps:Int,
    val kcal:Double,
    val distance:Float,
    val duration:Long
){

    fun toMap() = mapOf(
        "steps" to steps,
        "kcal" to kcal,
        "distance" to distance,
        "duration" to duration
    )

    companion object{
        fun toDailyAchievements(map:Map<*, *>) = DailyAchievements(
            steps = map["steps"]!!.toIntOrZero(),
            kcal = map["kcal"]!!.toDoubleOrZero(),
            distance = map["distance"]!!.toFloatOrZero(),
            duration = map["duration"]!!.toLongOrZero()
        )
    }
}
