package org.track.fit.auth.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.auth.states.SignInWithGoogleState
import org.track.fit.common.actions.CommonAction
import org.track.fit.common.actions.NavAction
import org.track.fit.common.ui.strings.UIText
import org.track.fit.ui.components.LifeCycleActions
import org.track.fit.ui.components.buttons.TFButton
import org.track.fit.ui.components.buttons.TFOutlineButton
import org.track.fit.ui.components.buttons.defaultButtonColors
import org.track.fit.ui.theme.localColors
import org.track.fit.ui.util.actions.UIActions
import trackfit.core.common.generated.resources.footprint_ic
import trackfit.core.common.generated.resources.google_login_ic
import trackfit.core.common.generated.resources.Res as SharedRes
import trackfit.feature.auth.generated.resources.Res
import trackfit.feature.auth.generated.resources.let_dive_into_account
import trackfit.feature.auth.generated.resources.let_get_started
import trackfit.feature.auth.generated.resources.login_with_google_btn
import trackfit.feature.auth.generated.resources.sign_in_btn
import trackfit.feature.auth.generated.resources.sign_up_btn

@Composable
fun AuthScreen(
    onNavToSignIn:()->Unit,
    onNavToSignUp: () -> Unit,
    onNavToHome:()->Unit,
    onNavToRegistration:()->Unit,
    state: SignInWithGoogleState,
    onShowSnackBar:(UIText)->Unit,
    uiActions: UIActions,
){

    LifeCycleActions(uiActions.actions){ action ->
        when(action){
            NavAction.NavToHome -> onNavToHome()
            NavAction.NavToRegistrationSteps -> onNavToRegistration()
            is CommonAction.ShowSnackBar -> onShowSnackBar(action.msg)
            else -> {}
        }
    }

    val loginWithGoogle = loginWithGoogle(state)

    Column(
        modifier = Modifier
            .background(MaterialTheme.localColors.surface)
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(Modifier.height(20.dp))

        Icon(
            painter = painterResource(
                resource = SharedRes.drawable.footprint_ic
            ),
            contentDescription = "foot_print",
            modifier = Modifier
                .size(60.dp),
            tint = MaterialTheme.localColors.primary
        )

        Spacer(Modifier.height(30.dp))

        Text(
            text = stringResource(Res.string.let_get_started),
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold, fontSize = 26.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.localColors.onBackground
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(Res.string.let_dive_into_account),
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.localColors.onSurfaceVariant
        )

        Spacer(Modifier.weight(0.1f))

        TFOutlineButton(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(Res.string.login_with_google_btn),
            leadingIcon = {
                Image(
                    painter = painterResource(SharedRes.drawable.google_login_ic),
                    contentDescription = "google_login_ic",
                    modifier = Modifier.size(20.dp)
                )
            },
            onClick = loginWithGoogle::login
        )

        Spacer(Modifier.height(30.dp))

        TFButton(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(Res.string.sign_up_btn),
            onClick = onNavToSignUp
        )

        Spacer(Modifier.height(16.dp))

        TFButton(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(Res.string.sign_in_btn),
            onClick = onNavToSignIn,
            colors = defaultButtonColors(
                container = MaterialTheme.localColors.secondaryContainer,
                content = MaterialTheme.localColors.onSecondaryContainer
                )
        )

        Spacer(Modifier.height(26.dp))
    }
}