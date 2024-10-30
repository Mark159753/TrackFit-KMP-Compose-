package org.track.fit.data.repository.location

import kotlinx.coroutines.flow.Flow
import org.track.fit.data.models.Location
import org.track.fit.data.models.RouteModel

interface LocationRepository {

    fun todayRoutes():Flow<List<RouteModel>>

    suspend fun saveTodayLocation(id:String, location: Location)

}