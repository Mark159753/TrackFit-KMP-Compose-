package org.track.fit.data.repository.location

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import org.track.fit.common.constants.LocationFBPath
import org.track.fit.common.date.now
import org.track.fit.common.date.toDayMilliseconds
import org.track.fit.common.date.toMilliseconds
import org.track.fit.data.models.Location
import org.track.fit.data.models.RouteModel

class LocationRepositoryImpl(
    private val database: DatabaseReference
) : LocationRepository {

    private val auth = Firebase.auth
    private val path: DatabaseReference
        get() = database.child(auth.currentUser?.uid + LocationFBPath)

    override fun todayRoutes(): Flow<List<RouteModel>> {
        val todayMillis = LocalDateTime.now().toDayMilliseconds()
        return path.child(todayMillis.toString()).valueEvents
            .catch { e ->
                Logger.e(tag = "todayRoutes", throwable = e, messageString = "ERROR")
            }
            .map { snapshot ->
            val mapa = if (snapshot.exists){
                snapshot.value as Map<String, Map<*, *>>
            }else emptyMap()

            mapa.filter { it.value.size > 2 }.map { item ->
                RouteModel(
                    id = item.key,
                    route = item.value.map {
                        val map = it.value as Map<*, *>
                        Location.fromMap(map)
                    }
                )
            }
        }
    }

    override suspend fun saveTodayLocation(id: String, location: Location) {
        val todayMillis = LocalDateTime.now().toDayMilliseconds().toString()
        val now = LocalDateTime.now().toMilliseconds().toString()
        path.child(todayMillis).child(id).child(now).setValue(location.toMap())
    }
}