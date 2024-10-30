package org.track.fit.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.track.fit.navigation.TrackFitNavHost
import org.track.fit.ui.components.AdaptiveScaffold
import org.track.fit.ui.components.bottom_navigation.TrackFitBottomNavBar
import org.track.fit.ui.components.bottom_navigation.TrackFitRailNavBar
import org.track.fit.ui.components.bottom_navigation.rememberTrackFitBottomNavState
import org.track.fit.ui.theme.LocalNavigationPaddings
import org.track.fit.ui.theme.TrackFitTheme
import org.track.fit.ui.theme.windowSize

@Composable
fun TrackFit(
    viewModel: MainViewModel = koinViewModel<MainViewModel>()
){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isDarkTheme by viewModel.state.isDarkTheme.collectAsStateWithLifecycle(isSystemInDarkTheme())
    val lng by viewModel.state.appLanguage.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val bottomBarState = rememberTrackFitBottomNavState(navController)

    KoinContext {
        TrackFitTheme(
            darkTheme = isDarkTheme,
            language = lng
        ) {
            AdaptiveScaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState) {
                        Snackbar(
                            snackbarData = it,
                            contentColor = Color.White,
                        )
                    }
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = bottomBarState.isVisible,
                        enter = slideInVertically(
                            animationSpec = tween(
                                durationMillis = 200,
                            ),
                            initialOffsetY = { it }
                        ),
                        exit = slideOutVertically(
                            animationSpec = tween(
                                durationMillis = 200,
                            ),
                            targetOffsetY = { it }
                        )
                    ) {
                        TrackFitBottomNavBar(
                            state = bottomBarState,
                            modifier = Modifier
                        )
                    }
                },
                railBar = {
                    if (bottomBarState.isVisible) {
                        TrackFitRailNavBar(
                            state = bottomBarState,
                            modifier = Modifier
                        )
                    }
                },
                contentWindowInsets = WindowInsets.safeDrawing,
                windowSizeClass = MaterialTheme.windowSize
            ) { paddings ->

                CompositionLocalProvider(
                    LocalNavigationPaddings provides paddings
                ){
                    TrackFitNavHost(
                        navController = navController,
                        onShowSnackBar = { msg ->
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState
                                    .showSnackbar(
                                        message = msg.getString(),
                                    )
                            }
                        }
                    )
                }
            }
        }
    }
}