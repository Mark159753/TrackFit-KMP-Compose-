package org.track.fit.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.NestedRoute
import org.track.fit.ui.navigation.lifecycleIsResumed
import org.track.fit.common.ui.strings.UIText

fun NavController.navigateToAuthGraph(navOptions: NavOptions? = null) {
    this.navigate(NestedRoute.AuthNavigation.name, navOptions)
}

fun NavGraphBuilder.authNestedNav(
    navController: NavHostController,
    onShowSnackBar:(UIText)->Unit,
){
    navigation(
        route = NestedRoute.AuthNavigation.name,
        startDestination = NavDestinations.Auth.route,
    ){
        authScreen(
            onNavToSignIn = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigateToSignIn()
                }
            },
            onNavToSignUp = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigateToSignUp()
                }
            },
            onNavToHome = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigate(
                        route = NavDestinations.Home.route,
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
                    navController.navigate(
                        route = NavDestinations.Registration.route,
                        navOptions =  NavOptions.Builder()
                            .setPopUpTo(
                                navController.graph.startDestinationRoute,
                                inclusive = true
                            )
                            .build()
                    )
                }
            },
            onShowSnackBar = onShowSnackBar
        )

        signInScreen(
            onNavBack = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigateUp()
                }
            },
            onShowSnackBar = onShowSnackBar,
            onNavToHome = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigate(
                        route = NavDestinations.Home.route,
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
                    navController.navigate(
                        route = NavDestinations.Registration.route,
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

        signUpScreen(
            onNavBack = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigateUp()
                }
            },
            onNavToRegistration = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigate(
                        route = NavDestinations.Registration.route,
                        navOptions =  NavOptions.Builder()
                            .setPopUpTo(
                                navController.graph.startDestinationRoute,
                                inclusive = true
                            )
                            .build()
                        )
                }
            },
            onNavToHome = {
                if (navController.currentBackStackEntry?.lifecycleIsResumed() == true) {
                    navController.navigate(
                        route = NavDestinations.Home.route,
                        navOptions =  NavOptions.Builder()
                            .setPopUpTo(
                                navController.graph.startDestinationRoute,
                                inclusive = true
                            )
                            .build()
                    )
                }
            },
            onShowSnackBar = onShowSnackBar
        )
    }
}