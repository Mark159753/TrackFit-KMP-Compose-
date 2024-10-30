package org.track.fit.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.track.fit.common.ui.strings.UIText
import org.track.fit.report.ui.ReportScreen
import org.track.fit.report.ui.ReportViewModel
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.SharedAxisDuration
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut

fun NavController.navigateToReport(navOptions: NavOptions? = null) {
    this.navigate(NavDestinations.Report.route, navOptions)
}

fun NavGraphBuilder.reportScreen(
    onShowSnackBar: (UIText)->Unit,
){
    composable(
        route = NavDestinations.Report.route,
        enterTransition = {
            when(initialState.destination.route){
                NavDestinations.Auth.route,
                NavDestinations.SignUp.route,
                NavDestinations.Registration.route,
                NavDestinations.SignIn.route -> materialSharedAxisZIn(forward = true, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        exitTransition = {
            when(targetState.destination.route){
                NavDestinations.Auth.route,
                NavDestinations.SignUp.route,
                NavDestinations.Registration.route,
                NavDestinations.SignIn.route -> materialSharedAxisZOut(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popEnterTransition = {
            when(initialState.destination.route){
                NavDestinations.Auth.route,
                NavDestinations.SignUp.route,
                NavDestinations.Registration.route,
                NavDestinations.SignIn.route -> materialSharedAxisZIn(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popExitTransition = {
            when(targetState.destination.route){
                NavDestinations.Auth.route,
                NavDestinations.SignUp.route,
                NavDestinations.Registration.route,
                NavDestinations.SignIn.route -> materialSharedAxisZOut(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        }
    ){

        val viewModel = koinViewModel<ReportViewModel>()

        ReportScreen(
            state = viewModel.state
            )
    }
}