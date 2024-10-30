package org.track.fit.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.track.fit.common.constants.DefaultStepsGoal
import org.track.fit.common.math.toRadians
import org.track.fit.ui.theme.localColors
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun TFStepsProgressIndicator(
    modifier: Modifier = Modifier,
    goal:Int = DefaultStepsGoal,
    steps:Int = 0,
    colors:StepsProgressIndicatorColors = defaultPIColors(),
    typography: StepsProgressTypography = defaultPITypography(),
    thin:Dp = 16.dp,
    title:String,
    button: @Composable ()->Unit
){

    val density = LocalDensity.current
    val trackThin = with(density){ thin.toPx() }
    val minutesHeight = with(density){ 4.dp.toPx() }
    val titleTopOffset = with(density){
        trackThin + minutesHeight + 22.dp.toPx()
    }
    val goalTitle = "/$goal"

    val startAngle = 120f
    val maxAngle = 300f

    val progress = remember { Animatable(initialValue = startAngle) }

    LaunchedEffect(steps, goal) {
        val maxValue = max(goal, steps)
        val p = steps / maxValue.toFloat()
        progress.animateTo(maxAngle * p)
    }

    val minutes = remember {
        (130 .. 410 step 20).toList().map { it.toFloat() }
    }

    val textMeasurer = rememberTextMeasurer()

    var stepsFontSize = remember(typography.steps.fontSize) {
        typography.steps.fontSize
    }

    val titleTextLayoutResult = remember(title, typography.title) {
        textMeasurer.measure(title, typography.title)
    }

    val goalTextLayoutResult = remember(goalTitle, typography.title) {
        textMeasurer.measure(title, typography.title)
    }

    BoxWithConstraints(
        modifier = modifier,
    ){
        val maxTextWidth = remember(trackThin, minutesHeight) {
            with(density){
                (maxWidth.toPx() - trackThin * 2 - minutesHeight * 2 - 20.dp.toPx()).roundToInt()
            }
        }

        val centerY = with(density){ maxHeight.toPx() / 2f }
        val centerX = with(density){ maxWidth.toPx() / 2f }
        val maxH = with(density){ maxHeight.toPx() }

        val stepsTextLayoutResult = remember(steps.toString()) {
            val text = steps.toString()
            var result = textMeasurer.measure(steps.toString(), typography.steps)
            while(result.size.width > maxTextWidth){
                stepsFontSize = with(density){ (stepsFontSize.toPx() - 10f).toSp() }
                result = textMeasurer.measure(text, typography.steps.copy(fontSize = stepsFontSize))
            }
            result
        }

        Canvas(
            modifier = Modifier
                .padding(thin / 2)
                .fillMaxSize()
                .aspectRatio(1f)
        ) {

            val cx = size.width / 2f
            val cy = size.height / 2f
            val radius = size.width / 2f - trackThin
            val minutesRadius = radius - minutesHeight

            drawArc(
                color = colors.path,
                startAngle = startAngle,
                sweepAngle = maxAngle,
                useCenter = false,
                style = Stroke(
                    width = trackThin,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )


            minutes.forEach { angel ->
                val angleRadians = toRadians(angel.toDouble())
                val endX = (cx + radius * cos(angleRadians)).toFloat()
                val endY = (cy + radius * sin(angleRadians)).toFloat()

                val startX = (cx + minutesRadius * cos(angleRadians)).toFloat()
                val startY = (cy + minutesRadius * sin(angleRadians)).toFloat()

                drawLine(
                    color = colors.path,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
            }


            drawArc(
                color = colors.track,
                startAngle = startAngle,
                sweepAngle = progress.value,
                useCenter = false,
                style = Stroke(
                    width = trackThin,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }

        Text(
            text = title,
            style = typography.title,
            color = colors.title,
            modifier = Modifier
                .graphicsLayer {
                    translationY = titleTopOffset
                    translationX = centerX - titleTextLayoutResult.size.width / 2
                }
        )

        Text(
            text = steps.toString(),
            style = typography.steps.copy(fontSize = stepsFontSize),
            color = colors.steps,
            modifier = Modifier
                .graphicsLayer {
                    translationY = centerY - stepsTextLayoutResult.size.height / 2
                    translationX = centerX - stepsTextLayoutResult.size.width / 2
                }
        )

        Text(
            text = goalTitle,
            style = typography.goal,
            color = colors.title,
            modifier = Modifier
                .graphicsLayer {
                    translationY = maxH - titleTopOffset * 2f
                    translationX = centerX - goalTextLayoutResult.size.width / 2
                }
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) { button() }
    }
}

@Composable
@ReadOnlyComposable
fun defaultPIColors(
    track:Color = MaterialTheme.localColors.primary,
    path:Color = MaterialTheme.localColors.outline,
    title: Color = MaterialTheme.localColors.outline,
    steps:Color = MaterialTheme.localColors.onSurface,
) = StepsProgressIndicatorColors(
    track = track,
    path = path,
    title = title,
    steps = steps
)

@Composable
@ReadOnlyComposable
fun defaultPITypography(
    title:TextStyle = MaterialTheme.typography.body1,
    steps:TextStyle = MaterialTheme.typography.h4,
    goal:TextStyle = MaterialTheme.typography.body1
) = StepsProgressTypography(
    title = title,
    steps = steps,
    goal = goal
)

@Immutable
data class StepsProgressIndicatorColors(
    val track:Color,
    val path:Color,
    val title:Color,
    val steps:Color
)

@Immutable
data class StepsProgressTypography(
    val title:TextStyle,
    val steps:TextStyle,
    val goal:TextStyle
)