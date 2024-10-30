package org.track.fit.track.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.track.fit.common.ui.strings.UIText
import org.track.fit.track.ui.TrackScreen
import org.track.fit.track.ui.TrackViewModel
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.SharedAxisDuration
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut

fun NavController.navigateToTrack(navOptions: NavOptions? = null) {
    this.navigate(NavDestinations.Track.route, navOptions)
}

fun NavGraphBuilder.trackScreen(
    onShowSnackBar: (UIText)->Unit,
){
    composable(
        route = NavDestinations.Track.route,
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

        val viewModel = koinViewModel<TrackViewModel>()

        TrackScreen(
            state = viewModel.state
        )
    }
}
