package org.track.fit.data.mappers

import org.track.fit.common.date.toHoursAndMinutes
import org.track.fit.common.exstantion.fromMetersToKm
import org.track.fit.common.exstantion.roundToString
import org.track.fit.common.ui.strings.UIText
import org.track.fit.data.models.DailyAchievements
import org.track.fit.data.models.PedometerData
import org.track.fit.data.models.PedometerDataUI
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.duration_format


fun PedometerData.toDailyAchievements() = DailyAchievements(
    steps = steps,
    kcal = kcal,
    distance = distance,
    duration = duration
)

fun DailyAchievements.toPedometerDataUi() = PedometerDataUI(
    steps = steps,
    kcal = kcal.roundToString(),
    distance = distance.fromMetersToKm(),
    duration = duration.toHoursAndMinutes().let { time ->
        UIText.ResString(Res.string.duration_format, arrayOf(time.first, time.second))
    }
)