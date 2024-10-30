package org.track.fit.ui.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry


fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED