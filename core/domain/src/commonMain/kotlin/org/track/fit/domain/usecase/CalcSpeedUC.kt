package org.track.fit.domain.usecase

class CalcSpeedUC {

    /**
     *  @param distance in meters
     *  @param startTime in milliseconds
     *  @param endTime in milliseconds
     * */
    operator fun invoke(
        distance:Float,
        startTime: Long,
        endTime: Long
    ): Double {
        val km = distance / 1000.0
        val timeDifferenceInSeconds = (endTime - startTime) / 1000.0

        // Convert to hours since speed is in km/h
        val timeDifferenceInHours = timeDifferenceInSeconds / 3600.0

        return km / timeDifferenceInHours
    }
}