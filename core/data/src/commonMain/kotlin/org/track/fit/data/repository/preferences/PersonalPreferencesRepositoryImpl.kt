package org.track.fit.data.repository.preferences

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.track.fit.common.constants.PersonalUserPreferencesPath

class PersonalPreferencesRepositoryImpl : PersonalPreferencesRepository {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val preferences:DocumentReference
        get() = db.document(PersonalUserPreferencesPath + auth.currentUser?.uid)

    override suspend fun isFieldExist(field: PersonalPreferences):Boolean{
        return try {
            preferences.get().contains(field.field)
        }catch (e:Exception){
            false
        }
    }

    override suspend fun getPreference(field: PersonalPreferences):String?{
        return try {
            preferences.get().get<String?>(field.field)
        }catch (e:CancellationException){
            throw e
        }catch (e:Exception){
            println("getPreference: ${field.field} -> $e")
            null
        }
    }

    override fun preferenceFlow(field: PersonalPreferences): Flow<String?> {
        return preferences.snapshots
            .catch { e ->
                Logger.e(tag = "preferenceFlow", throwable = e, messageString = "ERROR")
            }
            .map { snapshot ->
            if (snapshot.contains(field.field)){
                snapshot.get(field.field)
            }else{
                null
            }
        }
    }

    override suspend fun savePreference(field: PersonalPreferences, value: String) {
        try {
            if (preferences.get().exists){
                preferences.update(field.field to value)
            }else{
                preferences.set(mapOf(field.field to value))
            }
        }catch (e:Exception){
            println("savePreference -> ${e.message}, instanse -> $e")
        }
    }
}