package  org.track.fit.services.pedometer.dummy

import kotlinx.coroutines.flow.Flow
import org.track.fit.common.constants.Gender
import org.track.fit.data.repository.preferences.PersonalPreferences
import org.track.fit.data.repository.preferences.PersonalPreferencesRepository

class TestPersonalPreferencesRepository: PersonalPreferencesRepository {

    private val data = mutableMapOf(
        PersonalPreferences.Height to "180",
        PersonalPreferences.Weight to "80",
        PersonalPreferences.Gender to Gender.Male.name,
        PersonalPreferences.Age to "25",
        PersonalPreferences.StepsGoal to "5000"
    )

    override suspend fun isFieldExist(field: PersonalPreferences): Boolean {
        return data.containsKey(field)
    }

    override fun preferenceFlow(field: PersonalPreferences): Flow<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun savePreference(field: PersonalPreferences, value: String) {
        data[field] = value
    }

    override suspend fun getPreference(field: PersonalPreferences): String? {
        return data[field]
    }
}