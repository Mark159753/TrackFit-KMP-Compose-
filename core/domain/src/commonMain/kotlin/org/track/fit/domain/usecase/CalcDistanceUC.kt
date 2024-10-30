package org.track.fit.domain.usecase

class CalcDistanceUC {

    operator fun invoke(
        stepLength:Float,
        steps:Int
    ): Float {
        return steps * stepLength
    }
}