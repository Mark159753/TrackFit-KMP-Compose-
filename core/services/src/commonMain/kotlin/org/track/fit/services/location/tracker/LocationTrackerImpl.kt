package org.track.fit.services.location.tracker

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.track.fit.data.repository.location.LocationRepository
import org.track.fit.services.location.LocationProvider
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val TAG = "LocationTracker"

@OptIn(ExperimentalUuidApi::class)
class LocationTrackerImpl(
    private val repository:LocationRepository,
    private val locationProvider:LocationProvider
) : LocationTracker {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var observerJob: Job? = null

    private var uuid:Uuid = Uuid.random()

    override fun start() {
        uuid = Uuid.random()
        observerJob?.cancel()
        observerJob = scope.launch {
            locationProvider
                .locationFlow
                .catch { e ->
                    Logger.e(tag = TAG, throwable = e, messageString = "ERROR -> $e")
                }
                .collectLatest { location ->
                    repository.saveTodayLocation(uuid.toString(), location)
                }
        }
    }


    override fun stop() {
        observerJob?.cancel()
    }
}