package org.track.fit.ui.util.actions.permissions

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.accompanist.permissions.PermissionState as AndroidPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun rememberPermissionState(permission: Permission): PermissionState {
    return when (permission) {
        Permission.Location -> {
            val locationPermissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            val state = rememberMultiplePermissionsState(permissions = locationPermissions)
            remember(state) { MultiPermissionStateImpl(state) }
        }
        Permission.PhysicalActivity -> {
            val physicalPermissions = remember {
                mutableListOf<String>().apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        add(Manifest.permission.ACTIVITY_RECOGNITION)
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        add(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            val optionalPermissions = remember {
                mutableSetOf<String>().apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        add(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            if (physicalPermissions.isEmpty()) {
                remember { AlwaysGrantedPermissionState() }
            } else {
                val state = rememberMultiplePermissionsState(permissions = physicalPermissions)
                remember(state) { MultiPermissionStateImpl(state, optionalPermissions) }
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
internal class PermissionStateImpl(
    private val permissionState: AndroidPermissionState
):PermissionState{

    override val isGranted: Boolean
        get() = permissionState.status.isGranted

    override val shouldShowRationale: Boolean
        get() = permissionState.status.shouldShowRationale

    override fun requestPermission() {
        permissionState.launchPermissionRequest()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
internal class MultiPermissionStateImpl(
    private val permissionState: MultiplePermissionsState,
    private val optionalPermissions:Set<String> = emptySet()
):PermissionState{

    override val isGranted: Boolean
        get() {
            return if (permissionState.allPermissionsGranted || optionalPermissions.isEmpty()){
                permissionState.allPermissionsGranted
            }else{
                permissionState.permissions.filter { !it.status.isGranted }.all { p ->
                    optionalPermissions.contains(p.permission)
                }
            }
        }

    override val shouldShowRationale: Boolean
        get() = permissionState.shouldShowRationale

    override fun requestPermission() {
        permissionState.launchMultiplePermissionRequest()
    }
}

internal class AlwaysGrantedPermissionState():PermissionState{

    override val isGranted: Boolean = true

    override val shouldShowRationale: Boolean = false

    override fun requestPermission() {}
}