package org.track.fit.ui.components

import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdaptiveScaffold(
    modifier: Modifier = Modifier,
    contentWindowInsets: WindowInsets,
    windowSizeClass: WindowSizeClass,
    bottomBar: @Composable () -> Unit = {},
    railBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit,
){
    val safeInsets = remember(contentWindowInsets) {
        MutableWindowInsets(contentWindowInsets)
    }

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    Surface(
        modifier = modifier
            .onConsumedWindowInsetsChanged { consumedWindowInsets ->
                safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
            },
        color = backgroundColor,
        contentColor = contentColor
    ) {
        if (windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium){
            ExpandedScaffoldLayout(
                content = content,
                contentWindowInsets = safeInsets,
                snackbar = {
                    snackbarHost(snackbarHostState)
                },
                railBar = railBar
            )
        }else{
            CompactScaffoldLayout(
                content = content,
                contentWindowInsets = safeInsets,
                snackbar = {
                    snackbarHost(snackbarHostState)
                },
                bottomBar = bottomBar
            )
        }
    }
}

@Composable
private fun CompactScaffoldLayout(
    content: @Composable @UiComposable (PaddingValues) -> Unit,
    snackbar: @Composable @UiComposable () -> Unit,
    contentWindowInsets: WindowInsets,
    bottomBar: @Composable @UiComposable () -> Unit
){
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val snackbarPlaceables = subcompose(AdaptiveScaffoldLayoutContent.Snackbar, snackbar).fastMap {
            // respect only bottom and horizontal for snackbar and fab
            val leftInset = contentWindowInsets
                .getLeft(this@SubcomposeLayout, layoutDirection)
            val rightInset = contentWindowInsets
                .getRight(this@SubcomposeLayout, layoutDirection)
            val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
            // offset the snackbar constraints by the insets values
            it.measure(
                looseConstraints.offset(
                    -leftInset - rightInset,
                    -bottomInset
                )
            )
        }

        val snackbarHeight = snackbarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val bottomBarPlaceables = subcompose(AdaptiveScaffoldLayoutContent.BottomBar, bottomBar)
            .fastMap { it.measure(looseConstraints) }

        val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height

        val snackbarOffsetFromBottom = if (snackbarHeight != 0) {
            snackbarHeight +
                    (bottomBarHeight ?: contentWindowInsets.getBottom(this@SubcomposeLayout))
        } else {
            0
        }

        val bodyContentPlaceables = subcompose(AdaptiveScaffoldLayoutContent.MainContent) {
            val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
            val innerPadding = PaddingValues(
                top = insets.calculateTopPadding(),
                bottom =
                if (bottomBarPlaceables.isEmpty() || bottomBarHeight == null) {
                    insets.calculateBottomPadding()
                } else {
                    bottomBarHeight.toDp()
                },
                start = insets.calculateStartPadding((this@SubcomposeLayout).layoutDirection),
                end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection)
            )
            content(innerPadding)
        }.fastMap { it.measure(looseConstraints.copy(maxHeight = layoutHeight)) }

        layout(layoutWidth, layoutHeight) {
            bodyContentPlaceables.fastForEach {
                it.place(0, 0)
            }
            snackbarPlaceables.fastForEach {
                it.place(0, layoutHeight - snackbarOffsetFromBottom)
            }
            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceables.fastForEach {
                it.place(0, layoutHeight - (bottomBarHeight ?: 0))
            }
        }

    }
}

@Composable
private fun ExpandedScaffoldLayout(
    content: @Composable @UiComposable (PaddingValues) -> Unit,
    snackbar: @Composable @UiComposable () -> Unit,
    contentWindowInsets: WindowInsets,
    railBar: @Composable @UiComposable () -> Unit
){
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val railBarPlaceables = subcompose(AdaptiveScaffoldLayoutContent.RailBar, railBar)
            .fastMap { it.measure(looseConstraints) }

        val railBarWidth = railBarPlaceables.fastMaxBy { it.width }?.width

        val snackbarPlaceables = subcompose(AdaptiveScaffoldLayoutContent.Snackbar, snackbar).fastMap {
            // respect only bottom and horizontal for snackbar and fab
            val leftInset = contentWindowInsets
                .getLeft(this@SubcomposeLayout, layoutDirection)
            val rightInset = contentWindowInsets
                .getRight(this@SubcomposeLayout, layoutDirection) + (railBarWidth ?: 0)
            val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
            // offset the snackbar constraints by the insets values
            it.measure(
                looseConstraints.offset(
                    -leftInset - rightInset,
                    -bottomInset
                )
            )
        }

        val snackbarWidth = snackbarPlaceables.fastMaxBy { it.width }?.width ?: 0
        val snackbarHeight= snackbarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val snackbarOffsetFromLeft = if (snackbarWidth != 0) {
            (railBarWidth ?: contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection))
        } else {
            0
        }

        val bodyContentPlaceables = subcompose(AdaptiveScaffoldLayoutContent.MainContent) {
            val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
            val innerPadding = PaddingValues(
                top = insets.calculateTopPadding(),
                bottom = insets.calculateBottomPadding(),
                start = if (railBarPlaceables.isEmpty() || railBarWidth == null) {
                    insets.calculateStartPadding(layoutDirection)
                } else {
                    railBarWidth.toDp()
                },
                end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection)
            )
            content(innerPadding)
        }.fastMap { it.measure(looseConstraints.copy(maxHeight = layoutHeight)) }

        layout(layoutWidth, layoutHeight) {
            bodyContentPlaceables.fastForEach {
                it.place(0, 0)
            }
            snackbarPlaceables.fastForEach {
                it.place(snackbarOffsetFromLeft, layoutHeight - snackbarHeight)
            }
            // The rail bar is always at the left of the layout
            railBarPlaceables.fastForEach {
                it.place(0, 0)
            }
        }

    }

}

enum class AdaptiveScaffoldLayoutContent{ Snackbar,  BottomBar, RailBar, MainContent}