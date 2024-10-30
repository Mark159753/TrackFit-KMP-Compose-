package org.track.fit.services.service

import kotlinx.coroutines.flow.Flow

interface StepCounter {

    val steps:Flow<Int>

}

class StepCounterNotSupport(message:String? = null):Exception(message)
class StepCounterPermissionRequired(message:String? = null):Exception(message)