package org.track.fit.domain.usecase

import org.track.fit.common.constants.Gender

class CalcStepLengthUC{

    /**
     *  @param gender is a Gender enum
     *  @param height in cm
     *  @param weight in kg
     * */
    operator fun invoke(
        gender: Gender?,
        height:Int?,
        weight:Int?
    ): Float {
        val sex = gender ?: Gender.Male
        val h = height ?: if (sex == Gender.Male) DEFAULT_MALE_HEIGHT else DEFAULT_FEMALE_HEIGHT
        val w = weight ?: if (sex == Gender.Male) DEFAULT_MALE_WEIGHT else DEFAULT_FEMALE_WEIGHT

        val heightFactor = if (sex == Gender.Male) MALE_LENGTH_FACTOR else FEMALE_LENGTH_FACTOR
        val weightFactor = if (sex == Gender.Male) MALE_WEIGHT_FACTOR else FEMALE_WEIGHT_FACTOR

        return ((heightFactor * h) - (weightFactor * w)) / 100f
    }

}

private const val FEMALE_LENGTH_FACTOR = 0.413f
private const val MALE_LENGTH_FACTOR = 0.415f

private const val FEMALE_WEIGHT_FACTOR = 0.0015f
private const val MALE_WEIGHT_FACTOR = 0.001f

private const val DEFAULT_FEMALE_HEIGHT = 160
private const val DEFAULT_MALE_HEIGHT = 180

private const val DEFAULT_FEMALE_WEIGHT = 60
private const val DEFAULT_MALE_WEIGHT = 80
