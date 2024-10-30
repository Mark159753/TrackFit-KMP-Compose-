package org.track.fit.ui.util.actions.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface PermissionState {

    val isGranted:Boolean

    val shouldShowRationale:Boolean

    fun requestPermission()
}

@Composable
expect fun rememberPermissionState(permission: Permission):PermissionState