package org.track.fit.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.track.fit.auth.signIn.SignInScreen
import org.track.fit.auth.signIn.SignInViewModel
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.SharedAxisDuration
import org.track.fit.ui.navigation.SharedAxisSlideDiStance
import org.track.fit.common.ui.strings.UIText
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(NavDestinations.SignIn.route, navOptions)
}

internal fun NavGraphBuilder.signInScreen(
    onNavBack:()->Unit,
    onShowSnackBar:(UIText)->Unit,
    onNavToHome:()->Unit,
    onNavToRegistration:()->Unit
){
    composable(
        route = NavDestinations.SignIn.name,
        enterTransition = {
            when(initialState.destination.route){
                NavDestinations.Auth.route -> materialSharedAxisXIn(forward = true, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZIn(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        exitTransition = {
            when(targetState.destination.route){
                NavDestinations.Auth.route -> materialSharedAxisXOut(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZOut(forward = true, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popEnterTransition = {
            when(initialState.destination.route){
                NavDestinations.Auth.route -> materialSharedAxisXIn(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZIn(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popExitTransition = {
            when(targetState.destination.route){
                NavDestinations.Auth.route -> materialSharedAxisXOut(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZOut(forward = true, durationMillis = SharedAxisDuration)
                else -> null
            }
        }
    ){

        val viewModel = koinViewModel<SignInViewModel>()

        SignInScreen(
            onNavBack = onNavBack,
            state = viewModel.state,
            uiActions = viewModel,
            onShowSnackBar = onShowSnackBar,
            onNavToHome = onNavToHome,
            onNavToRegistration = onNavToRegistration
        )
    }
}