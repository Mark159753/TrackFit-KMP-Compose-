package org.track.fit.report.ui.calendar

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.StringResource
import org.track.fit.common.date.now
import org.track.fit.common.date.toShortDayName
import org.track.fit.common.ui.strings.UIText
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.full_april
import trackfit.core.common.generated.resources.full_august
import trackfit.core.common.generated.resources.full_december
import trackfit.core.common.generated.resources.full_february
import trackfit.core.common.generated.resources.full_january
import trackfit.core.common.generated.resources.full_july
import trackfit.core.common.generated.resources.full_june
import trackfit.core.common.generated.resources.full_march
import trackfit.core.common.generated.resources.full_may
import trackfit.core.common.generated.resources.full_november
import trackfit.core.common.generated.resources.full_october
import trackfit.core.common.generated.resources.full_september

@Stable
class ProgressCalendarState {

    private val now = LocalDateTime.Companion.now().date

    private val startDate = LocalDate(2000, 1, 1)
    private val endDate = LocalDate(2050, 12, 31)

    internal val startIndex:Int
        get() = calculateIndexForLocalDate(now)

    internal val totalMonth:Int
        get() {
            return (endDate.year - startDate.year) * 12 + (endDate.monthNumber - startDate.monthNumber + 1)
        }

    val dayNames:List<StringResource> by lazy {
        (1 .. 7).map { it.toShortDayName() }.toImmutableList()
    }

    internal fun getLocalDateForIndex(index: Int): LocalDate {
        return startDate.plus(index, DateTimeUnit.MONTH)
    }

    internal fun calculateIndexForLocalDate(yearMonth: LocalDate): Int {
        val yearsDifference = yearMonth.year - startDate.year
        val monthsDifference = yearMonth.monthNumber - startDate.monthNumber

        return yearsDifference * 12 + monthsDifference
    }

    internal fun getMonthYearHeadLine(index: Int): UIText.ResString {
        val date = getLocalDateForIndex(index)
        return when(date.month){
            Month.JANUARY -> UIText.ResString(Res.string.full_january, arrayOf(date.year.toString()))
            Month.FEBRUARY -> UIText.ResString(Res.string.full_february, arrayOf(date.year.toString()))
            Month.MARCH -> UIText.ResString(Res.string.full_march, arrayOf(date.year.toString()))
            Month.APRIL -> UIText.ResString(Res.string.full_april, arrayOf(date.year.toString()))
            Month.MAY -> UIText.ResString(Res.string.full_may, arrayOf(date.year.toString()))
            Month.JUNE -> UIText.ResString(Res.string.full_june, arrayOf(date.year.toString()))
            Month.JULY -> UIText.ResString(Res.string.full_july, arrayOf(date.year.toString()))
            Month.AUGUST -> UIText.ResString(Res.string.full_august, arrayOf(date.year.toString()))
            Month.SEPTEMBER -> UIText.ResString(Res.string.full_september, arrayOf(date.year.toString()))
            Month.OCTOBER -> UIText.ResString(Res.string.full_october, arrayOf(date.year.toString()))
            Month.NOVEMBER -> UIText.ResString(Res.string.full_november, arrayOf(date.year.toString()))
            Month.DECEMBER -> UIText.ResString(Res.string.full_december, arrayOf(date.year.toString()))
            else -> throw IllegalStateException("Unknown month type number ${date.month}")
        }
    }

    internal fun getPageDays(firstDayOfMonth:LocalDate): Map<Int, List<LocalDate>> {
        val result = mutableMapOf<Int, List<LocalDate>>()
        var startDay = findPageFirstDay(firstDayOfMonth)
        repeat(5){ row ->
            val daysOfMonth = mutableListOf<LocalDate>()
            repeat(7){
                daysOfMonth.add(startDay)
                startDay = startDay.plus(1, DateTimeUnit.DAY)
            }
            result[row] = daysOfMonth.toList()
        }
        return result
    }

    private fun findPageFirstDay(date:LocalDate): LocalDate {
        if (date.dayOfWeek.isoDayNumber == 1) return date
        return date.minus(date.dayOfWeek.isoDayNumber - 1, DateTimeUnit.DAY)
    }
}