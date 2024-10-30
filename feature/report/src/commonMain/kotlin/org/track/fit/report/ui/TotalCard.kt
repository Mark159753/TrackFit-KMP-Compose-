package org.track.fit.report.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.track.fit.report.ui.state.TotalState
import org.track.fit.ui.components.ColumnInfoItem
import org.track.fit.ui.components.columnInfoDefaults
import org.track.fit.ui.theme.localColors
import trackfit.core.common.generated.resources.kcal
import trackfit.core.common.generated.resources.kcal_burn_ic
import trackfit.core.common.generated.resources.km
import trackfit.core.common.generated.resources.location_ic
import trackfit.core.common.generated.resources.Res as SharedRes
import trackfit.feature.report.generated.resources.Res
import trackfit.core.common.generated.resources.outline_steps_ic
import trackfit.core.common.generated.resources.time
import trackfit.core.common.generated.resources.time_ic
import trackfit.feature.report.generated.resources.report_total_steps_title

@Composable
fun TotalCard(
    modifier: Modifier = Modifier,
    state:TotalState
){

    val total by state.total.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.localColors.surfaceContainer)
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        val stepsId = "inlineContent"
        val title = buildAnnotatedString {
            appendInlineContent(stepsId, "[icon]")
            append(" ")
            append(total.steps.toString())
        }

        val inlineContent = mapOf(
            Pair(
                stepsId,
                InlineTextContent(
                    Placeholder(
                        width = 26.sp,
                        height = 26.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                    )
                ) {
                    Icon(
                        painterResource(SharedRes.drawable.outline_steps_ic),
                        "",
                        tint = MaterialTheme.localColors.primary
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
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.localColors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            inlineContent = inlineContent,
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(Res.string.report_total_steps_title),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.localColors.outline,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.localColors.outline
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Row(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.localColors.surfaceContainer)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            ColumnInfoItem(
                modifier = Modifier
                    .weight(1f),
                colors = columnInfoDefaults(iconTint = MaterialTheme.localColors.timeIcon),
                icon = SharedRes.drawable.time_ic,
                title = stringResource(SharedRes.string.time),
                value = total.duration.asString()
            )


            Box(
                Modifier
                    .background(MaterialTheme.localColors.outline)
                    .width(1.dp)
                    .fillMaxHeight()
            )

            ColumnInfoItem(
                modifier = Modifier
                    .weight(1f),
                colors = columnInfoDefaults(iconTint = MaterialTheme.localColors.kcalIcon),
                icon = SharedRes.drawable.kcal_burn_ic,
                title = stringResource(SharedRes.string.kcal),
                value = total.kcal
            )

            Box(
                Modifier
                    .background(MaterialTheme.localColors.outline)
                    .width(1.dp)
                    .fillMaxHeight()
            )

            ColumnInfoItem(
                modifier = Modifier
                    .weight(1f),
                colors = columnInfoDefaults(iconTint = MaterialTheme.localColors.distanceIcon),
                icon = SharedRes.drawable.location_ic,
                title = stringResource(SharedRes.string.km),
                value = total.distance
            )
        }

    }
}