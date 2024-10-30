package org.track.fit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material.Typography
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.track.fit.common.constants.AppLanguage
import org.track.fit.ui.util.window_size.calculateWindowSizeClass

@Immutable
data class ColorSchema(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val outlineVariant: Color,
    val scrim: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val inversePrimary: Color,
    val surfaceDim: Color,
    val surfaceBright: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val timeIcon:Color,
    val kcalIcon:Color,
    val distanceIcon:Color,
    val stepsIcon:Color,
    val circularIndicator:Color,
    val circularBackground:Color,
    val circularText:Color,
)


private val lightScheme = ColorSchema(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
    timeIcon = orange55,
    kcalIcon = red56,
    distanceIcon = green43,
    stepsIcon = blue52,
    circularIndicator = purple63,
    circularBackground = purple92,
    circularText = gray68,
)

private val darkScheme = ColorSchema(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
    timeIcon = orange54,
    kcalIcon = red55,
    distanceIcon = green43,
    stepsIcon = blue51,
    circularIndicator = purple51,
    circularBackground = purple29,
    circularText = gray31,
)

internal val LocalColors = staticCompositionLocalOf {
    lightScheme
}

val MaterialTheme.localColors: ColorSchema
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current


@Composable
fun TrackFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    language:AppLanguage = AppLanguage.EN,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }

    val types = Typography(
        defaultFontFamily = getRalewayFrontFamily()
    )

    val windowSize = calculateWindowSizeClass()

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalIsDarkTheme provides darkTheme,
        LocalLocalization provides language,
        LocalWindowSizeClass provides windowSize
    ) {
        MaterialTheme(
            typography = types,
            content = content,
        )
    }
}

internal val LocalIsDarkTheme = staticCompositionLocalOf { false }

internal val LocalLocalization = staticCompositionLocalOf { AppLanguage.EN }

internal val LocalWindowSizeClass = staticCompositionLocalOf<WindowSizeClass> {
    noLocalProvidedFor("WindowSizeClass")
}

val LocalNavigationPaddings = staticCompositionLocalOf<PaddingValues> { PaddingValues.Absolute() }

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}

val MaterialTheme.isDarkTheme: Boolean
    @Composable
    @ReadOnlyComposable
    get() = LocalIsDarkTheme.current

val MaterialTheme.language: AppLanguage
    @Composable
    @ReadOnlyComposable
    get() = LocalLocalization.current

val MaterialTheme.windowSize: WindowSizeClass
    @Composable
    @ReadOnlyComposable
    get() = LocalWindowSizeClass.current