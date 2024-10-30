package org.track.fit.data.models

import kotlinx.datetime.LocalDate

data class DateAchievements(
    val date:LocalDate,
    val achievements: DailyAchievements?
)
