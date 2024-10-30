package org.track.fit.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.track.fit.ui.components.LifeCycleActions
import org.track.fit.ui.theme.localColors
import org.track.fit.common.actions.NavAction
import org.track.fit.ui.util.actions.UIActions
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.footprint_ic

@Composable
fun SplashScreen(
    onNavToAuth:()->Unit,
    onNavToRegistration:()->Unit,
    onNavToHome:()->Unit,
    uiActions: UIActions
){

    LifeCycleActions(uiActions.actions){ action ->
        when(action){
            NavAction.NavToHome -> onNavToHome()
            NavAction.NavToRegistrationSteps -> onNavToRegistration()
            NavAction.NavToAuth -> onNavToAuth()
            else ->{}
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.localColors.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.footprint_ic),
            contentDescription = null,
            tint = MaterialTheme.localColors.primary,
            modifier = Modifier.size(90.dp)
        )

        Spacer(Modifier.height(50.dp))

        CircularProgressIndicator(
            modifier = Modifier.size(30.dp),
            strokeWidth = 4.dp,
            color = MaterialTheme.localColors.tertiary,
            strokeCap = StrokeCap.Round
        )
    }
}