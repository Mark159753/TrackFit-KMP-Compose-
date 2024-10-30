package org.track.fit.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults.flingBehavior
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import org.track.fit.ui.theme.localColors
import org.track.fit.common.ui.strings.measureText
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalWheelPicker(
    modifier: Modifier = Modifier,
    items:List<Int>,
    startPosition:Int = 0,
    unit:String = "",
    onPositionChanged:(Int)->Unit = {}
){

    val pagerState = rememberPagerState(pageCount = {
        items.size
    }, initialPage = startPosition)

    LaunchedEffect(pagerState.currentPage){
        onPositionChanged(pagerState.currentPage)
    }

    BoxWithConstraints {

        val selectedHeight = 60.dp
        val verticalPadding = (maxHeight - selectedHeight) / 2

        val selectedColor = MaterialTheme.localColors.primary
        val ordinaryColor = MaterialTheme.localColors.onSurface
        val maxTextSize = measureText(items.last().toString(), MaterialTheme.typography.h3.copy(fontWeight = FontWeight.SemiBold))
        val unitX = (maxWidth / 2) + (maxTextSize.width / 2) + 12.dp

        val pickerVOffset = 6.dp
        val pickerLineWidth = with(LocalDensity.current){ 1.dp.toPx() }


        VerticalPager(
            modifier = modifier,
            state = pagerState,
            pageSize = PageSize.Fixed(selectedHeight),
            key = { index -> items[index] },
            flingBehavior = flingBehavior(
                state = pagerState,
            ),
            contentPadding = PaddingValues(vertical = verticalPadding)
        ){ index ->

            var textColor by remember {
                mutableStateOf(
                    if (pagerState.currentPage == index) selectedColor
                    else ordinaryColor
                )
            }

            Text(
                text = items[index].toString(),
                style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.SemiBold),
                color = textColor,
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
                                )

                        val p = (3f - pageOffset.absoluteValue.coerceIn(0f, 3f)) / 3f

                        textColor = lerp(ordinaryColor, selectedColor, p)


                        lerp(
                            start = 0.4f,
                            stop = 1f,
                            fraction = p,
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(selectedHeight + pickerVOffset *2)
                .offset(y = verticalPadding - pickerVOffset)
                .drawBehind {
                    drawLine(
                        color = selectedColor,
                        start = Offset(
                            x = size.width * 0.22f,
                            y = 0f
                        ),
                        end = Offset(
                            x = size.width * 0.78f,
                            y = 0f
                        ),
                        strokeWidth = pickerLineWidth
                    )

                    drawLine(
                        color = selectedColor,
                        start = Offset(
                            x = size.width * 0.22f,
                            y = size.height
                        ),
                        end = Offset(
                            x = size.width * 0.78f,
                            y = size.height
                        ),
                        strokeWidth = pickerLineWidth
                    )
                },
        ) {
            Text(
                text = unit,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
                color = ordinaryColor,
                modifier = Modifier.offset(
                    x = unitX,
                    y = maxTextSize.height * 0.44f + pickerVOffset
                )
            )
        }
    }


}