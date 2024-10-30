package org.track.fit.services.location

import co.touchlab.kermit.Logger
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.track.fit.data.models.Location
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationProviderImpl:LocationProvider {

    private val locationManager = CLLocationManager()

    @OptIn(ExperimentalForeignApi::class)
    override val locationFlow: Flow<Location> = callbackFlow {
        val observer = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                Logger.i("didUpdateLocations -> $didUpdateLocations")
                didUpdateLocations.lastOrNull()?.let { item ->
                    (item as? CLLocation)?.let { location ->
                        location.coordinate.useContents {
                            val result = Location(
                                lat = latitude,
                                lng = longitude
                            )
                            trySend(result)
                        }
                    }
                }
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                Logger.e("didFailWithError -> ${didFailWithError.localizedDescription}")
                close()
            }
        }

        locationManager.setDelegate(observer)
        locationManager.requestAlwaysAuthorization()
        locationManager.allowsBackgroundLocationUpdates = true
        if (locationManager.locationServicesEnabled()){
            locationManager.startUpdatingLocation()
        }else{
            close()
        }

        awaitClose {
            locationManager.stopUpdatingLocation()
            locationManager.setDelegate(null)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getCurrentLocation(): Location? = suspendCoroutine { continuation ->
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestWhenInUseAuthorization()

        val observer = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                didUpdateLocations.lastOrNull()?.let { item ->
                    (item as? CLLocation)?.let { location ->
                        location.coordinate.useContents {
                            continuation.resume(Location(
                                lat = latitude,
                                lng = longitude
                            ))
                        }
                        locationManager.stopUpdatingLocation()
                    }
                }
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                continuation.resume(null)
            }
        }

        locationManager.setDelegate(observer)
        if (locationManager.locationServicesEnabled()){
            locationManager.requestLocation()
        }else{
            continuation.resume(null)
        }
    }
}