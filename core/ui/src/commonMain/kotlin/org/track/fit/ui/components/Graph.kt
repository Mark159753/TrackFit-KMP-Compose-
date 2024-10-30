package org.track.fit.ui.components

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.track.fit.domain.usecase.statistics.GraphData
import org.track.fit.ui.theme.localColors

@Composable
fun Graph(
    modifier: Modifier = Modifier,
    list: ImmutableList<GraphData>,
    scale:ImmutableList<String>,
){

    var scaleTextHeight by remember { mutableStateOf(0) }
    val textHalfHeight = with(LocalDensity.current){ scaleTextHeight.toDp() / 2 }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(bottom = BarNameHeight)
                .fillMaxHeight()
        ) {
            scale.forEachIndexed { index, item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.localColors.outline,
                    modifier = Modifier
                        .then(
                            if (index == 0){
                                Modifier.onGloballyPositioned { scaleTextHeight = it.size.height }
                            }else Modifier
                        )
                )
            }
        }

        list.forEach {
            Bar(
                name = it.barName,
                progress = it.barProgress,
                modifier = Modifier.weight(1f).padding(vertical = textHalfHeight)
            )
        }
    }
}

@Composable
private fun Bar(
    modifier: Modifier = Modifier,
    name:String,
    @FloatRange(0.0, 1.0)
    progress: Float
){

    val animProgress = remember {
        Animatable(0f)
    }

    LaunchedEffect(progress){
        animProgress.animateTo(progress)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.localColors.primary,
                    shape = RoundedCornerShape(topStartPercent = 100, topEndPercent = 100)
                )
                .fillMaxWidth()
                .weight(1f, false)
                .fillMaxHeight(animProgress.value)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(BarNameHeight),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = name,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.localColors.outline,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

private val BarNameHeight = 28.dp