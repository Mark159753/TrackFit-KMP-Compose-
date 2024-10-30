package org.track.fit.common.date

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.short_friday
import trackfit.core.common.generated.resources.short_monday
import trackfit.core.common.generated.resources.short_saturday
import trackfit.core.common.generated.resources.short_sunday
import trackfit.core.common.generated.resources.short_thursday
import trackfit.core.common.generated.resources.short_tuesday
import trackfit.core.common.generated.resources.short_wednesday

fun LocalDateTime.Companion.now():LocalDateTime{
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.Companion.getCurrentWeekDays(): List<LocalDate> {
    val today = LocalDateTime.now()
    val monday = today.date.minus((today.dayOfWeek.ordinal - DayOfWeek.MONDAY.ordinal), DateTimeUnit.DAY)
    return (0..6).map {
        monday.plus(it, DateTimeUnit.DAY)
    }
}

fun LocalDateTime.toMilliseconds():Long{
    return toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun LocalDateTime.toDayMilliseconds():Long{
    return date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun Long.toLocalDateTime():LocalDateTime{
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Long.formatTimeDuration(): String {
    val totalSeconds = this / 1000
    val minutes = (totalSeconds / 60) % 60
    val hours = totalSeconds / 3600
    return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
}

fun Long.toHoursAndMinutes(): Pair<String, String> {
    val totalSeconds = this / 1000
    val minutes = (totalSeconds / 60) % 60
    val hours = totalSeconds / 3600
    return hours.toString() to minutes.toString()
}

/*
* 1 -> is Monday
* 7 -> is Sunday
* */

fun Int.toShortDayName():StringResource{
    return when(this){
        1 -> Res.string.short_monday
        2 -> Res.string.short_tuesday
        3 -> Res.string.short_wednesday
        4 -> Res.string.short_thursday
        5 -> Res.string.short_friday
        6 -> Res.string.short_saturday
        7 -> Res.string.short_sunday
        else -> throw IllegalStateException("Unknown day of $this")
    }
}