package org.track.fit.registration.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.track.fit.registration.ui.RegistrationScreen
import org.track.fit.registration.ui.RegistrationViewModel
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.SharedAxisDuration
import org.track.fit.ui.navigation.SharedAxisSlideDiStance
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut

fun NavController.navigateToRegistration(navOptions: NavOptions? = null) {
    this.navigate(NavDestinations.Registration.route, navOptions)
}

fun NavGraphBuilder.registrationScreen(
    onNavToHome:()->Unit,
){
    composable(
        route = NavDestinations.Registration.route,
        enterTransition = {
            when(initialState.destination.route){
                NavDestinations.SignUp.route -> materialSharedAxisXIn(forward = true, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        exitTransition = {
            when(targetState.destination.route){
                NavDestinations.SignUp.route -> materialSharedAxisXOut(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popEnterTransition = {
            when(initialState.destination.route){
                NavDestinations.SignUp.route -> materialSharedAxisXIn(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popExitTransition = {
            when(targetState.destination.route){
                NavDestinations.SignUp.route -> materialSharedAxisXOut(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                else -> null
            }
        }
    ){

        val viewModel = koinViewModel<RegistrationViewModel>()
        RegistrationScreen(
            state = viewModel.state,
            uiActions = viewModel,
            onNavToHome = onNavToHome
        )
    }
}