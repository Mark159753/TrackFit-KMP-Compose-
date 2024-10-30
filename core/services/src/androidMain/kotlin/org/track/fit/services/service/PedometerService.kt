package org.track.fit.services.service

import android.app.ForegroundServiceStartNotAllowedException
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.track.fit.common.exstantion.hasPostNotificationPermission
import org.track.fit.data.repository.statistics.StatisticsRepository
import org.track.fit.services.location.tracker.LocationTracker
import org.track.fit.services.notification.NotificationHelper

private val TAG = "PedometerService"

class PedometerService:Service() {

    private val binder = PedometerServiceBinder()

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var subscriberJob: Job? = null
    private var pedometerJob: Job? = null

    private val pedometerManager:PedometerManager = get()
    private val statisticsRepository: StatisticsRepository = get()
    private val locationTracker:LocationTracker = get()

    val state:StateFlow<PedometerState> = pedometerManager.state

    inner class PedometerServiceBinder : Binder() {
        fun getService(): PedometerService = this@PedometerService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.i(tag = TAG, messageString = "onStartCommand")
        if (intent?.action == ACTION_STOP_PEDOMETER){
            stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        pedometerJob?.cancel()
        locationTracker.stop()
        Logger.i(tag = TAG, messageString = "onDestroy")
    }

    fun start(){
        pedometerJob?.cancel()
        pedometerJob = scope.launch {
            try {
                pedometerManager.startObserving()
            }catch (e:Exception){
                stop()
                locationTracker.stop()
            }
        }
        locationTracker.start()
        startForeground()
        observeDataUpdates()
    }

    fun stop(){
        locationTracker.stop()
        cancelJobs()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun cancelJobs(){
        subscriberJob?.cancel()
        pedometerJob?.cancel()
    }


    private fun startForeground(){
        scope.launch {
            try {
                val data = statisticsRepository.todayAchievements.firstOrNull()
                val notification = NotificationHelper.createPedometerNotification(applicationContext, data)
                ServiceCompat.startForeground(
                    this@PedometerService,
                    NotificationHelper.PEDOMETER_NOTIFICATION_ID,
                    notification,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH
                    } else {
                        0
                    }
                )
            }catch (e:Exception){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                    && e is ForegroundServiceStartNotAllowedException
                ) {
                    Logger.e(tag = TAG, throwable = e, messageString = "Require foreground permission")
                }else{
                    Logger.e(tag = TAG, throwable = e, messageString = "ERROR")
                }
            }
        }
    }

    private fun observeDataUpdates(){
        if (!applicationContext.hasPostNotificationPermission()) return
        subscriberJob?.cancel()
        subscriberJob = scope.launch {
            statisticsRepository.todayAchievements.collectLatest { data ->
                NotificationHelper.updatePedometerNotification(applicationContext, data)
            }
        }
    }

    companion object{
        const val ACTION_STOP_PEDOMETER = "ACTION_STOP_PEDOMETER"
    }
}