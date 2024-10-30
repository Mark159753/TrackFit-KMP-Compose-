package org.track.fit.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun IntrinsicMinRow(
    modifier: Modifier = Modifier,
    spaceBy:Dp = 16.dp,
    content: @Composable IntrinsicMinRowScope.()->Unit
){

    val spaceByPx = with(LocalDensity.current){ spaceBy.toPx().roundToInt() }

    Layout(
        modifier = modifier,
        content = { IntrinsicMinRowScope.content() },
        measurePolicy = { measurable, constraints ->

            val layoutWidth = constraints.maxWidth

            val intrinsicMeasurable = measurable.firstOrNull { it.parentData is IntrinsicMin }
            val parentDataCount = measurable.mapNotNull { it.parentData as? IntrinsicMin }.count()

            check(parentDataCount == 1 && intrinsicMeasurable != null){ "Must be one composable with intrinsicMin Modifier" }

            val columnWidth = max( (layoutWidth / measurable.size) - ((measurable.size -1) * spaceByPx / 2), 50)

            val looseConstraints = constraints.copy(minWidth = columnWidth, minHeight = 0, maxWidth = columnWidth)

            val maxHeightPlaceable = intrinsicMeasurable.measure(looseConstraints)

            val maxHeightConstraints = constraints.copy(minWidth = columnWidth, minHeight = maxHeightPlaceable.height, maxWidth = columnWidth, maxHeight = maxHeightPlaceable.height)

            val placeable = measurable.map {
                if (it.parentData is IntrinsicMin){
                    maxHeightPlaceable
                }else{
                    it.measure(maxHeightConstraints)
                }
            }

            var xPos = 0

            layout(layoutWidth, maxHeightPlaceable.height){
                placeable.forEach { item ->
                    item.place(
                        x = xPos,
                        y = 0
                    )
                    xPos += spaceByPx
                    xPos += item.width
                }
            }
        }
    )
}

interface IntrinsicMinRowScope{

    fun Modifier.intrinsicMin() = this.then(IntrinsicMin())

    companion object: IntrinsicMinRowScope
}

private class IntrinsicMin:ParentDataModifier{
    override fun Density.modifyParentData(parentData: Any?) = this@IntrinsicMin
}