package org.track.fit.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.track.fit.splash.ui.SplashScreen
import org.track.fit.splash.ui.SplashViewModel
import org.track.fit.ui.navigation.NavDestinations

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    this.navigate(NavDestinations.Splash.route, navOptions)
}

fun NavGraphBuilder.splashScreen(
    onNavToAuth:()->Unit,
    onNavToRegistration:()->Unit,
    onNavToHome:()->Unit,
){
    composable(
        route = NavDestinations.Splash.route,
    ){

        val viewModel = koinViewModel<SplashViewModel>()

        SplashScreen(
            onNavToAuth = onNavToAuth,
            onNavToRegistration = onNavToRegistration,
            onNavToHome = onNavToHome,
            uiActions = viewModel
        )
    }
}