package org.track.fit.track.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.services.service.PedometerState
import org.track.fit.services.service.registerPedometerServiceManager
import org.track.fit.track.ui.state.TrackState
import org.track.fit.ui.components.TFToolBar
import org.track.fit.ui.components.buttons.TFButton
import org.track.fit.ui.theme.LocalNavigationPaddings
import org.track.fit.ui.theme.localColors
import org.track.fit.ui.util.actions.permissions.Permission
import org.track.fit.ui.util.actions.permissions.rememberPermissionState
import trackfit.core.common.generated.resources.my_location_ic
import trackfit.feature.track.generated.resources.Res
import trackfit.core.common.generated.resources.Res as Shared
import trackfit.feature.track.generated.resources.tracks_location_permission_required
import trackfit.feature.track.generated.resources.tracks_start_btn
import trackfit.feature.track.generated.resources.tracks_title

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrackScreen(
    state:TrackState
){

    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
    )
    val scaffoldHeight = remember {
        mutableStateOf(0)
    }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val locationPermissionState = rememberPermissionState(Permission.Location)

    val pedometerService = registerPedometerServiceManager()
    val pedometerState by pedometerService.state.collectAsStateWithLifecycle()

    val isMyLocationEnabled by state.isMyLocationEnabled.collectAsStateWithLifecycle()

    val navigationPaddings = LocalNavigationPaddings.current
    val bottomPadding = navigationPaddings.calculateBottomPadding()


    LaunchedEffect(
        key1 = locationPermissionState.isGranted,
        key2 = locationPermissionState.shouldShowRationale
    ){
        if (!locationPermissionState.shouldShowRationale && !locationPermissionState.isGranted){
            locationPermissionState.requestPermission()
        }
    }

    LaunchedEffect(pedometerState){
        when(pedometerState){
            PedometerState.Running -> bottomSheetState.expand()
            PedometerState.Stop -> bottomSheetState.collapse()
            else ->{}
        }
    }


    BottomSheetScaffold(
        modifier = Modifier
            .background(MaterialTheme.localColors.surface)
            .statusBarsPadding()
            .fillMaxSize()
            .onGloballyPositioned { scaffoldHeight.value = it.size.height },
        topBar = {
            TFToolBar(
                title = stringResource(Res.string.tracks_title),
                modifier = Modifier
                    .background(MaterialTheme.localColors.surface)
                    .padding(
                        start = navigationPaddings.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                        end = navigationPaddings.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
                    )
            )
        },
        sheetContent = {
            TrackBottomSheet(
                onStop = pedometerService::stop,
                state = state
            )
        },
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetPeekHeight = 0.dp,
        sheetGesturesEnabled = false,
        sheetBackgroundColor = MaterialTheme.localColors.surfaceContainer,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMapView(
                modifier = Modifier
                    .fillMaxSize(),
                state = state
            )

            AnimatedVisibility(
                visible = locationPermissionState.shouldShowRationale,
                enter = slideInVertically(
                    initialOffsetY = { it }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { -it }
                ),
                modifier = Modifier
                    .padding(
                        start = navigationPaddings.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                        end = navigationPaddings.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
                    )
                    .align(Alignment.TopCenter)
            ){
                LocationPermissionNeed(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .padding(
                        bottom = bottomPadding,
                        start = navigationPaddings.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                        end = navigationPaddings.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
                    )
            ) {
                IconButton(
                    onClick = { state.onToggleTrackUser(track = true) },
                    enabled = isMyLocationEnabled,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 16.dp)
                        .size(56.dp)
                        .graphicsLayer {
                            val offset = bottomSheetState.requireOffset() - scaffoldHeight.value
                            translationY = if (offset < 0){
                                offset * 0.5f
                            }else{
                                offset
                            }
                        }
                        .background(
                            color = if (isMyLocationEnabled) MaterialTheme.localColors.primary else MaterialTheme.localColors.primary.copy(alpha = 0.6f),
                            shape = CircleShape
                        )
                        .padding(8.dp)
                ){
                    Icon(
                        painter = painterResource(Shared.drawable.my_location_ic),
                        contentDescription = null,
                        tint = MaterialTheme.localColors.onPrimary,
                        modifier = Modifier.fillMaxSize()
                    )
                }


                TFButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    title = stringResource(Res.string.tracks_start_btn),
                    onClick = pedometerService::start
                )

            }
        }

    }
}

@Composable
private fun LocationPermissionNeed(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .background(MaterialTheme.localColors.errorContainer)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ){
        Text(
            text = stringResource(Res.string.tracks_location_permission_required),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.localColors.onErrorContainer
        )
    }
}