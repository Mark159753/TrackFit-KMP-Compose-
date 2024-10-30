package org.track.fit.local.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.track.fit.local.preferences.APP_PREFERENCES
import java.io.File

actual fun provideDataStoreModule() = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { File(androidApplication().filesDir, "datastore/$APP_PREFERENCES").path.toPath() }
        )
    }
}