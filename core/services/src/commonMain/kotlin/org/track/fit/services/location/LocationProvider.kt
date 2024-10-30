package org.track.fit.services.location

import kotlinx.coroutines.flow.Flow
import org.track.fit.data.models.Location

interface LocationProvider {

    val locationFlow: Flow<Location>

    suspend fun getCurrentLocation(): Location?
}