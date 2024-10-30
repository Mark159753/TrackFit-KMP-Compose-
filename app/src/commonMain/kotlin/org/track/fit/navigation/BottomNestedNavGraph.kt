package org.track.fit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import org.track.fit.account.navigation.accountScreen
import org.track.fit.auth.navigation.navigateToAuthGraph
import org.track.fit.home.navigation.homeScreen
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.NestedRoute
import org.track.fit.common.ui.strings.UIText
import org.track.fit.report.navigation.reportScreen
import org.track.fit.track.navigation.trackScreen

fun NavGraphBuilder.bottomNestedNavGraph(
    navController: NavHostController,
    onShowSnackBar:(UIText)->Unit,
){
    navigation(
        route = NestedRoute.BottomNavigation.name,
        startDestination = NavDestinations.Home.route,
    ){
        homeScreen(
            onShowSnackBar = onShowSnackBar,
            onNavToAuth = {
                navController.navigateToAuthGraph(
                    navOptions =  NavOptions.Builder()
                        .setPopUpTo(
                            navController.graph.startDestinationRoute,
                            inclusive = true
                        )
                        .build()
                )
            },
        )

        trackScreen(
            onShowSnackBar = onShowSnackBar,
        )

        reportScreen(
            onShowSnackBar = onShowSnackBar,
        )

        accountScreen(
            onShowSnackBar = onShowSnackBar,
            onNavToAuth = {
                navController.navigateToAuthGraph(
                    navOptions =  NavOptions.Builder()
                        .setPopUpTo(
                            navController.graph.startDestinationRoute,
                            inclusive = true
                        )
                        .build()
                )
            }
        )
    }
}