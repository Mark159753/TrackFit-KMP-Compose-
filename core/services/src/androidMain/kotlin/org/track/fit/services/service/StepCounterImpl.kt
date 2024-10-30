package org.track.fit.services.service

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import co.touchlab.kermit.Logger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.track.fit.common.exstantion.hasStepCounterPermission

private const val TAG = "STEP_COUNTER"

class StepCounterImpl(
    private val context: Context
): StepCounter {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private var lastSteps:Int = -1

    override val steps: Flow<Int>
        get() = subscribeStepCounter()


    private fun subscribeStepCounter() = callbackFlow{
        Logger.d(tag = TAG, messageString = "Registering sensor listener... ")

        val havePermission = context.hasStepCounterPermission()
        if (!havePermission){
            close(StepCounterPermissionRequired())  // throws StepCounterPermissionRequired exception
            return@callbackFlow
        }

        val listener: SensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return

                val stepsSinceLastReboot = event.values[0].toInt()
                Logger.d(tag = TAG, messageString = "Steps since last reboot: $stepsSinceLastReboot")
                if (lastSteps == -1){
                    lastSteps = stepsSinceLastReboot
                }

                trySend(stepsSinceLastReboot - lastSteps)

                lastSteps = stepsSinceLastReboot
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Logger.d(tag = TAG, messageString = "Accuracy changed to: $accuracy")
            }
        }

        val supportedAndEnabled = sensorManager.registerListener(listener,
            sensor, SensorManager.SENSOR_DELAY_UI)
        Logger.d(tag = TAG, messageString = "Sensor listener registered: $supportedAndEnabled")

        if (!supportedAndEnabled){
            close(StepCounterNotSupport()) // throws StepCounterNotSupport exception
        }
        awaitClose {
            Logger.d(tag = TAG,   messageString = "Stopping sensor updates")
            lastSteps = -1
            sensorManager.unregisterListener(listener, sensor)
        }
    }
}