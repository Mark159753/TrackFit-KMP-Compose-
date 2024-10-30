package org.track.fit.registration.ui.steps

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.registration.ui.state.GenderState
import org.track.fit.ui.theme.localColors
import trackfit.feature.registration.generated.resources.Res
import trackfit.feature.registration.generated.resources.registration_gender
import trackfit.feature.registration.generated.resources.registration_gender_subtitle
import trackfit.feature.registration.generated.resources.registration_select_your
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenderStep(
    modifier: Modifier = Modifier,
    state: GenderState
){

    val pagerState = rememberPagerState { state.items.size }

    LaunchedEffect(pagerState.currentPage){
        state.setCurrentPosition(pagerState.currentPage)
    }

    val primaryColor = MaterialTheme.localColors.primary
    val ordinaryColor = MaterialTheme.localColors.surfaceDim
    val ovalHeight = with(LocalDensity.current){ 42.dp.toPx() }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val title = buildAnnotatedString {
            append(stringResource(Res.string.registration_select_your))
            append(" ")
            withStyle(
                SpanStyle(color = MaterialTheme.localColors.primary)
            ){
                append(stringResource(Res.string.registration_gender))
            }
        }
        StepTitle(
            title = title,
            subtitle = stringResource(Res.string.registration_gender_subtitle)
        )

        Spacer(Modifier.height(18.dp))

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 80.dp),
            modifier = Modifier.weight(1f)
        ){ index ->

            val item = state.items[index]

            var ovalColor by remember {
                mutableStateOf(
                    if (pagerState.currentPage == index) primaryColor
                    else ordinaryColor
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
                                )

                        val p = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)

                        ovalColor = androidx.compose.ui.graphics.lerp(ordinaryColor, primaryColor, p)

                        lerp(
                            start = 0.7f,
                            stop = 1f,
                            fraction = p,
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        lerp(
                            start = if (index == 1) -(size.width * 0.28f) else (size.width * 0.3f),
                            stop = 0f,
                            fraction = p,
                        ).also { scale ->
                            translationX = scale
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(item.img),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .drawBehind {
                            drawOval(
                                color = ovalColor,
                                topLeft = Offset(
                                    x = 0f,
                                    y = size.height - ovalHeight
                                ),
                                size = Size(
                                    width = size.width,
                                    height = ovalHeight
                                )
                            )
                        },
                    contentScale = ContentScale.FillHeight
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(item.title),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(modifier = Modifier.height(16.dp))

            }
        }

    }
}