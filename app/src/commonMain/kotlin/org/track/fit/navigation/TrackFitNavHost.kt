package org.track.fit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import org.track.fit.auth.navigation.authNestedNav
import org.track.fit.auth.navigation.navigateToAuthGraph
import org.track.fit.home.navigation.navigateToHome
import org.track.fit.registration.navigation.navigateToRegistration
import org.track.fit.registration.navigation.registrationScreen
import org.track.fit.splash.navigation.splashScreen
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.lifecycleIsResumed
import org.track.fit.common.ui.strings.UIText

@Composable
fun TrackFitNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination:String = NavDestinations.Splash.route,
    onShowSnackBar:(UIText)->Unit,
){
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination,
    ){

        splashScreen(
            onNavToHome = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigateToHome(
                        navOptions =  NavOptions.Builder()
                            .setPopUpTo(
                                navController.graph.startDestinationRoute,
                                inclusive = true
                            )
                            .build()
                    )
                }
            },
            onNavToRegistration = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigateToRegistration(
                        navOptions =  NavOptions.Builder()
                            .setPopUpTo(
                                navController.graph.startDestinationRoute,
                                inclusive = true
                            )
                            .build()
                    )
                }
            },
            onNavToAuth = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigateToAuthGraph(
                        navOptions =  NavOptions.Builder()
                            .setPopUpTo(
                                navController.graph.startDestinationRoute,
                                inclusive = true
                            )
                            .build()
                    )
                }
            }
        )

        authNestedNav(
            navController = navController,
            onShowSnackBar = onShowSnackBar
        )

        registrationScreen(
            onNavToHome = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigateToHome(
                        navOptions =  NavOptions.Builder()
                            .setPopUpTo(
                                navController.graph.startDestinationRoute,
                                inclusive = true
                            )
                            .build()
                    )
                }
            }
        )

        bottomNestedNavGraph(
            navController = navController,
            onShowSnackBar = onShowSnackBar,
        )
    }
}