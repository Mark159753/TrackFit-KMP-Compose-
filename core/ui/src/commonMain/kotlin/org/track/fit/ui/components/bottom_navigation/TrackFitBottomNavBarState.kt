package org.track.fit.ui.components.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import org.track.fit.ui.navigation.NavDestinations

@Composable
fun rememberTrackFitBottomNavState(
    navController: NavHostController = rememberNavController(),
): TrackFitBottomNavBarState {
    return remember(
        navController
    ) {
        TrackFitBottomNavBarState(
            navController,
        )
    }
}

@Stable
class TrackFitBottomNavBarState(
    private val navController: NavHostController
) {

    val currentDestination: String?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route

    val currentBottomBarDestination: BottomBarDestinations?
        @Composable get() = when (currentDestination) {
            NavDestinations.Home.route -> BottomBarDestinations.Home
            NavDestinations.Track.route  -> BottomBarDestinations.Track
            NavDestinations.Report.route  -> BottomBarDestinations.Report
            NavDestinations.Account.route -> BottomBarDestinations.Account
            else -> null
        }

    val isVisible:Boolean
        @Composable get() = currentBottomBarDestination != null

    val bottomBarDestinations: List<BottomBarDestinations> = BottomBarDestinations.entries

    fun navigateToBottomBarDestination(bottomBarDestination: BottomBarDestinations) {
        String::class.simpleName
        val navOps = navOptions {

            popUpTo(navController.graph.findStartDestination().route ?: NavDestinations.Home.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when(bottomBarDestination){
            BottomBarDestinations.Home -> navController.navigate(NavDestinations.Home.route, navOps)
            BottomBarDestinations.Track -> navController.navigate(NavDestinations.Track.route, navOps)
            BottomBarDestinations.Report -> navController.navigate(NavDestinations.Report.route, navOps)
            BottomBarDestinations.Account -> navController.navigate(NavDestinations.Account.route, navOps)
        }
    }

}