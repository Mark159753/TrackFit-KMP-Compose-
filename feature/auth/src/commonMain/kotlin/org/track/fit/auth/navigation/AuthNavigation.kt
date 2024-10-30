package org.track.fit.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.track.fit.auth.auth.AuthScreen
import org.track.fit.auth.auth.AuthViewModel
import org.track.fit.common.ui.strings.UIText
import org.track.fit.ui.navigation.NavDestinations
import org.track.fit.ui.navigation.SharedAxisDuration
import org.track.fit.ui.navigation.SharedAxisSlideDiStance
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut

fun NavController.navigateAuth(navOptions: NavOptions? = null) {
    this.navigate(NavDestinations.Auth.route, navOptions)
}

internal fun NavGraphBuilder.authScreen(
    onNavToSignIn:()->Unit,
    onNavToSignUp: () -> Unit,
    onNavToHome:()->Unit,
    onNavToRegistration:()->Unit,
    onShowSnackBar:(UIText)->Unit,
){
    composable(
        route = NavDestinations.Auth.route,
        enterTransition = {
            when(initialState.destination.route){
                NavDestinations.SignUp.route,
                NavDestinations.SignIn.route-> materialSharedAxisXIn(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZIn(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        exitTransition = {
            when(targetState.destination.route){
                NavDestinations.SignUp.route,
                NavDestinations.SignIn.route -> materialSharedAxisXOut(forward = true, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZOut(forward = true, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popEnterTransition = {
            when(initialState.destination.route){
                NavDestinations.SignUp.route,
                NavDestinations.SignIn.route -> materialSharedAxisXIn(forward = false, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZIn(forward = false, durationMillis = SharedAxisDuration)
                else -> null
            }
        },
        popExitTransition = {
            when(targetState.destination.route){
                NavDestinations.SignUp.route,
                NavDestinations.SignIn.route -> materialSharedAxisXOut(forward = true, slideDistance = SharedAxisSlideDiStance, durationMillis = SharedAxisDuration)
                NavDestinations.Home.route -> materialSharedAxisZOut(forward = true, durationMillis = SharedAxisDuration)
                else -> null
            }
        }
    ){

        val viewModel = koinViewModel<AuthViewModel>()

        AuthScreen(
            onNavToSignIn = onNavToSignIn,
            onNavToSignUp = onNavToSignUp,
            onNavToHome = onNavToHome,
            onNavToRegistration = onNavToRegistration,
            state = viewModel.state,
            onShowSnackBar = onShowSnackBar,
            uiActions = viewModel
        )
    }
}