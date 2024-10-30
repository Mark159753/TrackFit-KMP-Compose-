package org.track.fit.services.service

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.track.fit.services.di.ServiceDIHelper
import org.track.fit.services.location.tracker.LocationTracker

private const val TAG = "PedometerService"

class PedometerServiceManagerImpl:PedometerServiceManager {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var pedometerJob: Job? = null

    private val di = ServiceDIHelper()

    private val pedometerManager:PedometerManager = di.providePedometerManager()
    private val locationTracker: LocationTracker = di.provideLocationTracker()

    override val state: StateFlow<PedometerState> = pedometerManager.state


    override fun start(){
        pedometerJob?.cancel()
        pedometerJob = scope.launch {
            try {
                pedometerManager.startObserving()
                locationTracker.start()
            }catch (e:Exception){
                Logger.e(tag = TAG, throwable = e, messageString = "ERROR")
                stop()
            }
        }
    }

    override fun stop(){
        pedometerJob?.cancel()
        locationTracker.stop()
    }

}