package org.track.fit.services.service

import co.touchlab.kermit.Logger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.CoreMotion.CMPedometer
import platform.Foundation.NSDate
import platform.Foundation.date

private const val TAG = "STEP_COUNTER"

class StepCounterImpl:StepCounter {

    val pedometer = CMPedometer()

    override val steps: Flow<Int>
        get() = subscribeStepCounter()

    private fun subscribeStepCounter() = callbackFlow{
        if (!CMPedometer.isStepCountingAvailable()){
            close(StepCounterNotSupport()) // throws StepCounterNotSupport exception
            return@callbackFlow
        }

        pedometer.startPedometerUpdatesFromDate(start = NSDate.date()){ data, error ->
            Logger.d(tag = TAG, messageString = "Registering sensor listener... ")
            if (error != null){
                Logger.e(tag = TAG,   messageString = error.localizedDescription)
                close()
            }

            data?.numberOfSteps?.intValue?.let { steps ->
                Logger.d(tag = TAG, messageString = "Steps since last reboot: $steps")
                trySend(steps)
            }
        }

        awaitClose {
            Logger.d(tag = TAG,   messageString = "Stopping location updates")
            pedometer.stopPedometerUpdates()
        }
    }
}