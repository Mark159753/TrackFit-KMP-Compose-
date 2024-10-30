package org.track.fit.services.service.pedometer

import kotlinx.coroutines.flow.Flow
import org.track.fit.data.models.PedometerData

interface Pedometer {

    val data:Flow<PedometerData>

}
