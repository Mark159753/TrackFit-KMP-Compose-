package org.track.fit.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.track.fit.auth.signUp.SignUpScreen
import org.track.fit.auth.signUp.SignUpViewModel
import org.track.fit.common.ui.strings.UIText
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.SharedAxisDuration
import org.track.fit.ui.navigation.SharedAxisSlideDiStance
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(NavDestinations.SignUp.name, navOptions)
}

internal fun NavGraphBuilder.signUpScreen(
    onNavBack:()->Unit,
    onNavToRegistration:()->Unit,
    onNavToHome:()->Unit,
    onShowSnackBar:(UIText)->Unit,
){
    composable(
        route = NavDestinations.SignUp.route,
        enterTransition = {
            when(initialState.destination.route){
                NavDestinations.Auth.route -> materialSharedAxisXIn(forward = true, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Registration.route -> materialSharedAxisXIn(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZIn(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        exitTransition = {
            when(targetState.destination.route){
                NavDestinations.Auth.route -> materialSharedAxisXOut(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Registration.route -> materialSharedAxisXOut(forward = true, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZOut(forward = true, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popEnterTransition = {
            when(initialState.destination.route){
                NavDestinations.Auth.route -> materialSharedAxisXIn(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Registration.route -> materialSharedAxisXIn(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZIn(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popExitTransition = {
            when(targetState.destination.route){
                NavDestinations.Auth.route -> materialSharedAxisXOut(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Registration.route -> materialSharedAxisXOut(forward = true, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZOut(forward = true, durationMillis = SharedAxisDuration)
                else -> null
            }
        }
    ){
        val viewModel = koinViewModel<SignUpViewModel>()

        SignUpScreen(
            onNavBack = onNavBack,
            onNavToRegistration = onNavToRegistration,
            onNavToHome = onNavToHome,
            onShowSnackBar = onShowSnackBar,
            state = viewModel.state,
            uiActions = viewModel
        )
    }
}