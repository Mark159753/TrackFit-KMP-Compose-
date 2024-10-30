package org.track.fit

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.track.fit.di.appModules
import org.track.fit.services.notification.NotificationHelper

class TrackFitApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        startKoin {
            androidContext(this@TrackFitApp)
            androidLogger()
            modules(appModules())
        }
        NotificationHelper.createChanel(this)
    }
}