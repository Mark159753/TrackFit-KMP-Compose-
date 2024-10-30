package org.track.fit.domain.usecase

class CalcCaloriesUC {

    /**
     *  @param speed in km/h
     *  @param weight in kg
     *  @param duration in milliseconds
     * */
    operator fun invoke(
        speed:Double,
        weight:Int,
        duration:Long
    ): Double {
        val durationHours = duration / 1000.0 / 3600.0
        return getMETbySpeed(speed) * weight * durationHours
    }

    private fun getMETbySpeed(speed:Double) = when{
        speed < 4.0 -> SLOW_WALK_MET
        speed < 5.6 -> MODERATE_WALK_MET
        else -> BRISK_WALK_MET
    }
}

private const val SLOW_WALK_MET = 2.4f
private const val MODERATE_WALK_MET = 3.3f
private const val BRISK_WALK_MET = 4.5f