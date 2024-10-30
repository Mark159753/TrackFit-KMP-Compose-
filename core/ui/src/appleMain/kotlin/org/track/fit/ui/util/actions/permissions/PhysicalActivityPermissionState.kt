package org.track.fit.ui.util.actions.permissions

import platform.CoreMotion.CMAuthorizationStatusAuthorized
import platform.CoreMotion.CMAuthorizationStatusDenied
import platform.CoreMotion.CMMotionActivityManager
import platform.CoreMotion.CMPedometer
import platform.Foundation.NSDate
import platform.Foundation.date

class PhysicalActivityPermissionState:PermissionState {

    override val isGranted: Boolean
        get() = CMMotionActivityManager.authorizationStatus() == CMAuthorizationStatusAuthorized

    override val shouldShowRationale: Boolean
        get() = CMMotionActivityManager.authorizationStatus() == CMAuthorizationStatusDenied

    override fun requestPermission() {
        val pedometer = CMPedometer()
        pedometer.startPedometerUpdatesFromDate(start = NSDate.date()){ _, _ -> }
        pedometer.stopPedometerUpdates()
    }
}