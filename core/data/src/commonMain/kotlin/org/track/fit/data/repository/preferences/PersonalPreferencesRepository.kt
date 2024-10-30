package org.track.fit.data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface PersonalPreferencesRepository {

    suspend fun isFieldExist(field: PersonalPreferences):Boolean

    suspend fun savePreference(field: PersonalPreferences, value:String)

    suspend fun getPreference(field: PersonalPreferences):String?

    fun preferenceFlow(field: PersonalPreferences):Flow<String?>

}