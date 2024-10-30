package org.track.fit.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.track.fit.common.actions.CommonAction
import org.track.fit.common.actions.NavAction
import org.track.fit.common.ui.strings.UIText
import org.track.fit.home.ui.state.HomeState
import org.track.fit.services.service.PedometerState
import org.track.fit.services.service.registerPedometerServiceManager
import org.track.fit.ui.components.IntrinsicMinRow
import org.track.fit.ui.components.LifeCycleActions
import org.track.fit.ui.components.TFToolBar
import org.track.fit.ui.dialog.PermissionDialogProp
import org.track.fit.ui.dialog.PermissionRequestDialog
import org.track.fit.ui.theme.LocalNavigationPaddings
import org.track.fit.ui.theme.localColors
import org.track.fit.ui.theme.windowSize
import org.track.fit.ui.util.actions.UIActions
import org.track.fit.ui.util.actions.permissions.Permission
import org.track.fit.ui.util.actions.permissions.PermissionState
import org.track.fit.ui.util.actions.permissions.rememberPermissionState
import trackfit.core.common.generated.resources.permission_physical_dialog_msg
import trackfit.core.common.generated.resources.permission_physical_dialog_title
import trackfit.core.common.generated.resources.physical_therapy_ic
import trackfit.feature.home.generated.resources.Res
import trackfit.core.common.generated.resources.Res as SharedRes
import trackfit.feature.home.generated.resources.home_title

@Composable
fun HomeScreen(
    onShowSnackBar:(UIText)->Unit,
    onNavToAuth:()->Unit,
    uiActions: UIActions,
    state:HomeState,
){

    LifeCycleActions(uiActions.actions){ action ->
        when(action){
            is CommonAction.ShowSnackBar -> onShowSnackBar(action.msg)
            NavAction.NavToAuth -> onNavToAuth()
            else -> {}
        }
    }

    val pedometerService = registerPedometerServiceManager()
    val pedometerState by pedometerService.state.collectAsStateWithLifecycle()

    val physicalActivityPermission = rememberPermissionState(Permission.PhysicalActivity)

    var displayPermissionDialog:Boolean by remember { mutableStateOf(false) }

    val navigationPaddings = LocalNavigationPaddings.current

    PermissionRequestDialog(
        onDismiss = { displayPermissionDialog = false },
        isShowingDialog = displayPermissionDialog,
        onGrantClick = {
            displayPermissionDialog = false
            physicalActivityPermission.requestPermission()
        },
        properties = PermissionDialogProp(
            icon = SharedRes.drawable.physical_therapy_ic,
            title = SharedRes.string.permission_physical_dialog_title,
            msg = SharedRes.string.permission_physical_dialog_msg
        )
    )

    Column(
        modifier = Modifier
            .background(MaterialTheme.localColors.surface)
            .statusBarsPadding()
            .padding(
                start = navigationPaddings.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                end = navigationPaddings.calculateEndPadding(layoutDirection = LocalLayoutDirection.current))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TFToolBar(
            title = stringResource(Res.string.home_title)
        )

        Box(modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
        ) {

            if (MaterialTheme.windowSize.widthSizeClass >= WindowWidthSizeClass.Medium){
                MediumContent(
                    state = state,
                    physicalActivityPermission = physicalActivityPermission,
                    pedometerState = pedometerState,
                    onStartPedometer = pedometerService::start,
                    onStopPedometer = pedometerService::stop,
                    onDisplayPermissionDialog = { displayPermissionDialog = true }
                )
            }else{
                CompactContent(
                    state = state,
                    physicalActivityPermission = physicalActivityPermission,
                    navigationPaddings = navigationPaddings,
                    pedometerState = pedometerState,
                    onStartPedometer = pedometerService::start,
                    onStopPedometer = pedometerService::stop,
                    onDisplayPermissionDialog = { displayPermissionDialog = true }
                )
            }

            this@Column.AnimatedVisibility(
                visible = pedometerState is PedometerState.Error,
                enter = slideInVertically(
                    initialOffsetY = { -it }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it }
                ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ) {
                ErrorMsg(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = (pedometerState as? PedometerState.Error)?.error?.msg?.asString() ?: ""
                )
            }
        }
    }
}

@Composable
private fun CompactContent(
    modifier: Modifier = Modifier,
    state:HomeState,
    physicalActivityPermission:PermissionState,
    navigationPaddings:PaddingValues,
    pedometerState:PedometerState,
    onStartPedometer:()->Unit,
    onStopPedometer:()->Unit,
    onDisplayPermissionDialog:()->Unit,
){
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = navigationPaddings.calculateBottomPadding())
            .fillMaxSize()
    ) {

        Spacer(Modifier.height(16.dp))

        ProgressCounterCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                ),
            isRunning = pedometerState == PedometerState.Running,
            onStartClick = {
                if (physicalActivityPermission.isGranted) {
                    onStartPedometer()
                } else {
                    onDisplayPermissionDialog()
                }
            },
            onStopClick = onStopPedometer,
            state = state
        )

        Spacer(Modifier.height(16.dp))

        QuestInfoCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                ),
            state = state
        )

        Spacer(Modifier.height(16.dp))

        DailyProgressCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                ),
            state = state
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun MediumContent(
    modifier: Modifier = Modifier,
    state:HomeState,
    physicalActivityPermission:PermissionState,
    pedometerState:PedometerState,
    onStartPedometer:()->Unit,
    onStopPedometer:()->Unit,
    onDisplayPermissionDialog:()->Unit,
){
    Box(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        IntrinsicMinRow(
            modifier = modifier
                .fillMaxWidth(),
            spaceBy = 16.dp
        ) {
            ProgressCounterCard(
                modifier = Modifier
                    .fillMaxHeight(),
                isRunning = pedometerState == PedometerState.Running,
                onStartClick = {
                    if (physicalActivityPermission.isGranted) {
                        onStartPedometer()
                    } else {
                        onDisplayPermissionDialog()
                    }
                },
                onStopClick = onStopPedometer,
                state = state
            )

            Column(
                modifier = Modifier
                    .intrinsicMin()
            ) {
                QuestInfoCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = state
                )

                Spacer(Modifier.height(16.dp))

                DailyProgressCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = state
                )
            }
        }
    }
}


@Composable
private fun ErrorMsg(
    modifier: Modifier = Modifier,
    text:String,
){
    Box(
        modifier = modifier
            .background(MaterialTheme.localColors.errorContainer)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ){
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.localColors.onErrorContainer
        )
    }
}