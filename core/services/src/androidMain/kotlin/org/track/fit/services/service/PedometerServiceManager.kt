package org.track.fit.services.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "PedometerServiceManager"

class PedometerServiceManagerImpl(
    private val context: Context
):PedometerServiceManager,
    DefaultLifecycleObserver {

    private val _state = MutableStateFlow<PedometerState>(PedometerState.Stop)
    override val state: StateFlow<PedometerState>
        get() = _state

    private var mService: PedometerService? = null
    private var mBound: Boolean = false

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var subscriberJob:Job? = null


    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Logger.i(tag = TAG, messageString = "Pedometer Service Connected")
            val binder = service as PedometerService.PedometerServiceBinder
            mService = binder.getService()
            mBound = true
            subscribeOnState()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Logger.i(tag = TAG, messageString = "Pedometer Service Disconnected")
            subscriberJob?.cancel()
            mBound = false
            mService = null
        }
    }


    override fun start() {
        mService?.start()
        Intent(context, PedometerService::class.java).also { intent ->
            context.startForegroundService(intent)
        }
    }

    override fun stop() {
        mService?.stop()
        Intent(context, PedometerService::class.java).also { intent ->
            context.stopService(intent)
        }
    }


    override fun onStart(owner: LifecycleOwner) {
        Logger.i(tag = TAG, messageString = "on lifecycle Start")
        Intent(context, PedometerService::class.java).also { intent ->
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        Logger.i(tag = TAG, messageString = "on lifecycle Stop")
        subscriberJob?.cancel()
        context.unbindService(connection)
        mBound = false
        mService = null
    }

    private fun subscribeOnState(){
        if (mService == null) return
        subscriberJob?.cancel()
        subscriberJob = scope.launch {
            mService?.state?.collectLatest { state -> _state.value = state }
        }
    }
}