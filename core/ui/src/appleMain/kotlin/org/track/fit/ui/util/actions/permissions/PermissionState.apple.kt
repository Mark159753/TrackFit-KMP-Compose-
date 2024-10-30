package org.track.fit.ui.util.actions.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.darwin.NSObject

@Composable
actual fun rememberPermissionState(permission: Permission): PermissionState {
    return remember(permission) {
        when(permission){
            Permission.Location -> LocationPermissionState()
            Permission.PhysicalActivity -> PhysicalActivityPermissionState()
        }
    }
}

internal class LocationPermissionState:PermissionState {

    private val locationManager = CLLocationManager()

    private val listener:CLLocationManagerDelegateProtocol = object : NSObject(), CLLocationManagerDelegateProtocol{
        override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
            _isGranted.value = checkIfGranted()
        }
    }

    init {
        locationManager.delegate = listener
    }

    private var _isGranted = mutableStateOf(checkIfGranted())
    override val isGranted: Boolean
        get() = _isGranted.value

    private var _shouldShowRationale = mutableStateOf(checkShouldShowRationale())
    override val shouldShowRationale: Boolean
        get() = _shouldShowRationale.value

    override fun requestPermission() {
        if (locationManager.authorizationStatus == kCLAuthorizationStatusNotDetermined) {
            locationManager.requestAlwaysAuthorization()
        } else {
            _shouldShowRationale.value = checkShouldShowRationale()
            _isGranted.value = checkIfGranted()
        }
    }


    private fun checkIfGranted():Boolean{
        return when (locationManager.authorizationStatus) {
            kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> true
            else -> false
        }
    }

    private fun checkShouldShowRationale():Boolean{
        return !checkIfGranted()
                && (locationManager.authorizationStatus != kCLAuthorizationStatusNotDetermined)
    }
}