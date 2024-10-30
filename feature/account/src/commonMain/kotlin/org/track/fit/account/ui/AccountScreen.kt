package org.track.fit.account.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.track.fit.account.ui.state.AccountState
import org.track.fit.common.actions.CommonAction
import org.track.fit.common.actions.NavAction
import org.track.fit.common.constants.AppLanguage
import org.track.fit.common.ui.strings.UIText
import org.track.fit.ui.components.LifeCycleActions
import org.track.fit.ui.components.TFToolBar
import org.track.fit.ui.dialog.ChangeLanguageDialog
import org.track.fit.ui.theme.LocalNavigationPaddings
import org.track.fit.ui.theme.localColors
import trackfit.feature.account.generated.resources.Res
import trackfit.feature.account.generated.resources.account_is_dark
import trackfit.feature.account.generated.resources.account_language
import trackfit.feature.account.generated.resources.account_logout_btn
import trackfit.feature.account.generated.resources.account_title

@Composable
fun AccountScreen(
    onShowSnackBar:(UIText)->Unit,
    state:AccountState,
    onNavToAuth:()->Unit,
){

    LifeCycleActions(state.uiActions.actions){ action ->
        when(action){
            is CommonAction.ShowSnackBar -> onShowSnackBar(action.msg)
            NavAction.NavToAuth -> onNavToAuth()
            else -> {}
        }
    }

    val navigationPaddings = LocalNavigationPaddings.current
    val bottomPadding = navigationPaddings.calculateBottomPadding()

    var displayLanguageDialog by remember {
        mutableStateOf(false)
    }

    val isDark by state.isDarkTheme.collectAsStateWithLifecycle(isSystemInDarkTheme())
    val currentLng by state.currentLng.collectAsStateWithLifecycle(AppLanguage.EN)

    if (displayLanguageDialog){
        ChangeLanguageDialog(
            current = currentLng,
            onSelect = remember {
                {
                    state.changeLng(it)
                    displayLanguageDialog = false
                }
            },
            onDismiss = { displayLanguageDialog = false }
        )
    }

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.localColors.surface)
            .statusBarsPadding()
            .fillMaxSize(),
        backgroundColor = MaterialTheme.localColors.surface,
        topBar = {
            TFToolBar(
                title = stringResource(Res.string.account_title),
                modifier = Modifier
                    .padding(
                        start = navigationPaddings.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                        end = navigationPaddings.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
                    )
                    .background(MaterialTheme.localColors.surface)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = bottomPadding,
                    start = navigationPaddings.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                    end = navigationPaddings.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PreferenceSwitchRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                isSelected = isDark,
                onCheckedChange = state::toggleTheme,
                text = stringResource(Res.string.account_is_dark)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(resource = Res.string.account_language),
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp),
                color = MaterialTheme.localColors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { displayLanguageDialog = true }
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = state::logout,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(stringResource(Res.string.account_logout_btn))
            }
        }
    }
}