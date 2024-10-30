package org.track.fit.ui.components.bottom_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.ui.theme.localColors

val NAVIGATION_BAR_HEIGHT = 64.dp

@Composable
fun TrackFitBottomNavBar(
    modifier: Modifier = Modifier,
    state:TrackFitBottomNavBarState = rememberTrackFitBottomNavState(),
    colors: NavigationColors = bottomNavigationVColors()
){
    Row(
        modifier = modifier
            .background(colors.container)
            .navigationBarsPadding()
            .height(NAVIGATION_BAR_HEIGHT)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        state.bottomBarDestinations.forEach { destination ->
            val selected = state.currentBottomBarDestination == destination
            NavigationItem(
                selected = selected,
                onClick = { state.navigateToBottomBarDestination(destination) },
                icon = painterResource(destination.icon),
                label = stringResource(resource = destination.title),
                colors = colors,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TrackFitRailNavBar(
    modifier: Modifier = Modifier,
    state:TrackFitBottomNavBarState = rememberTrackFitBottomNavState(),
    colors: NavigationColors = bottomNavigationVColors()
){

    val navBarInsets = with(LocalDensity.current){
        WindowInsets.navigationBars.getLeft(this, LocalLayoutDirection.current).toDp()
    }

    Column(
        modifier = modifier
            .background(colors.container)
            .statusBarsPadding()
            .padding(start = navBarInsets)
            .width(NAVIGATION_BAR_HEIGHT)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        state.bottomBarDestinations.forEach { destination ->
            val selected = state.currentBottomBarDestination == destination
            NavigationItem(
                selected = selected,
                onClick = { state.navigateToBottomBarDestination(destination) },
                icon = painterResource(destination.icon),
                label = stringResource(resource = destination.title),
                colors = colors,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    label: String,
    colors: NavigationColors = bottomNavigationVColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
){

    val selectedColor by animateColorAsState(
        targetValue = if (selected) colors.selectedItemBg else colors.container, label = "select_bg"
    )
    val labelColor by animateColorAsState(
        targetValue = if (selected) colors.selectedLabel else colors.label, label = "select_label"
    )

    val p = animateFloatAsState(if (selected) 1f else 0f)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
        ) {
        Column(
            modifier = modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .drawBehind {
                        scale(scale = p.value){
                            drawRoundRect(
                                color = selectedColor,
                                cornerRadius = CornerRadius(size.height / 2f, size.height / 2f)
                            )
                        }
                    }
                    .padding(vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = labelColor
                )
            }

            Spacer(Modifier.height(2.dp))

            AnimatedVisibility(visible = selected) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold),
                    color = labelColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun bottomNavigationVColors() = NavigationColors(
    container = MaterialTheme.localColors.surfaceContainer,
    selectedItemBg = MaterialTheme.localColors.secondaryContainer,
    selectedLabel = MaterialTheme.localColors.onSecondaryContainer,
    label = MaterialTheme.localColors.onSurface
)

@Immutable
data class NavigationColors(
    val container:Color,
    val selectedItemBg:Color,
    val selectedLabel:Color,
    val label: Color
)