package org.track.fit.auth.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.auth.auth.loginWithGoogle
import org.track.fit.auth.signUp.state.SignUpState
import org.track.fit.common.actions.CommonAction
import org.track.fit.common.actions.NavAction
import org.track.fit.common.ui.strings.UIText
import org.track.fit.ui.components.LifeCycleActions
import org.track.fit.ui.components.buttons.TFButton
import org.track.fit.ui.components.buttons.TFOutlineButton
import org.track.fit.ui.dialog.FullScreenLoading
import org.track.fit.ui.theme.localColors
import org.track.fit.ui.util.actions.UIActions
import trackfit.core.common.generated.resources.arrow_back_ic
import trackfit.core.common.generated.resources.google_login_ic
import trackfit.core.common.generated.resources.person_ic
import trackfit.feature.auth.generated.resources.Res
import trackfit.feature.auth.generated.resources.login_with_google_btn
import trackfit.feature.auth.generated.resources.sign_up_btn
import trackfit.core.common.generated.resources.Res as SharedRes
import trackfit.feature.auth.generated.resources.sign_up_subtitle
import trackfit.feature.auth.generated.resources.sign_up_title

@Composable
fun SignUpScreen(
    onNavBack:()->Unit,
    onNavToRegistration:()->Unit,
    onNavToHome:()->Unit,
    onShowSnackBar:(UIText)->Unit,
    state: SignUpState,
    uiActions: UIActions
){

    LifeCycleActions(uiActions.actions){ action ->
        when(action){
            CommonAction.NavBack -> onNavBack()
            is CommonAction.ShowSnackBar -> onShowSnackBar(action.msg)
            NavAction.NavToHome -> onNavToHome()
            NavAction.NavToRegistrationSteps -> onNavToRegistration()
            else -> {}
        }
    }

    val loginWithGoogle = loginWithGoogle(state)

    FullScreenLoading(
        isShowingDialog = state.isLoading
    )

    Column(
        modifier = Modifier
            .background(MaterialTheme.localColors.surface)
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        val btnTranslation = with(LocalDensity.current){ 10.dp.toPx() }
        IconButton(
            onClick = onNavBack,
            modifier = Modifier
                .align(Alignment.Start)
                .graphicsLayer {
                    translationX = -btnTranslation
                }
        ){
            Icon(
                painter = painterResource(SharedRes.drawable.arrow_back_ic),
                contentDescription = "arrow_back",
                tint = MaterialTheme.localColors.onSurface
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(20.dp))

            val titleId = "inlineContent"
            val title = buildAnnotatedString {
                append(stringResource(Res.string.sign_up_title))
                append(" ")
                appendInlineContent(titleId, "[icon]")
            }

            val inlineContent = mapOf(
                Pair(
                    titleId,
                    InlineTextContent(
                        Placeholder(
                            width = 26.sp,
                            height = 26.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                        )
                    ) {
                        Icon(
                            painterResource(SharedRes.drawable.person_ic),
                            "",
                            tint = MaterialTheme.localColors.onBackground
                        )
                    }
                )
            )

            Text(
                text = title,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.localColors.onBackground,
                inlineContent = inlineContent
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = stringResource(Res.string.sign_up_subtitle),
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.localColors.onSurfaceVariant
            )

            Spacer(Modifier.height(40.dp))

            EmailField(
                state = state.emailState
            )

            Spacer(Modifier.height(16.dp))

            PasswordField(
                state = state.passwordState
            )

            Spacer(Modifier.height(30.dp))

            OrLine()

            Spacer(Modifier.height(16.dp))

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

            Spacer(
                Modifier
                    .height(30.dp)
            )
        }

        TFButton(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(Res.string.sign_up_btn),
            onClick = state::onSignUp
        )

        Spacer(
            Modifier
                .height(16.dp)
        )

    }
}

@Composable
internal fun OrLine(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.localColors.outline,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "or",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.localColors.outline,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Divider(
            thickness = 1.dp,
            color = MaterialTheme.localColors.outline,
            modifier = Modifier.weight(1f)
        )
    }
}