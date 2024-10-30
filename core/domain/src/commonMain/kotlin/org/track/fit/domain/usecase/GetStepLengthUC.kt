package org.track.fit.domain.usecase

import org.track.fit.common.constants.toGender
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository

class GetStepLengthUC(
    private val preferences: PersonalPreferencesRepository,
    private val calcStepLengthUC: CalcStepLengthUC
) {

    suspend operator fun invoke(): Float {
        val gender = preferences.getPreference(PersonalPreferences.Gender)?.toGender()
        val weight = preferences.getPreference(PersonalPreferences.Weight)?.toInt()
        val height = preferences.getPreference(PersonalPreferences.Height)?.toInt()
        return calcStepLengthUC(gender, height, weight)
    }
}