package abdulmanov.eduard.timetable.presentation._common.extensions

import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*

fun getDaysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()

    if(firstDayOfWeek != DayOfWeek.MONDAY){
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)

        daysOfWeek = rhs + lhs
    }

    return daysOfWeek
}

fun getFullTitlesDaysOfWeekFromLocale(): List<String> {
    val daysOfWeek = getDaysOfWeekFromLocale()

    return daysOfWeek.map {
        val name = it.getDisplayName(TextStyle.FULL, Locale.getDefault())
        name.first().toUpperCase() + name.slice(1 until name.length)
    }
}

fun getFullTitleDayOfWeekForNumber(number: Int): String {
    val fullTitles = getFullTitlesDaysOfWeekFromLocale()
    return fullTitles[number - 1]
}

fun getNumberForFullTitleDayOfWeek(dayOfWeek: String): Int {
    val fullTitles = getFullTitlesDaysOfWeekFromLocale()
    return fullTitles.indexOf(dayOfWeek) + 1
}

fun getMonthsForCalendar(): Pair<YearMonth, YearMonth> {
    val currentMonth = YearMonth.now()

    val startYear = if(currentMonth.monthValue >= 9) currentMonth.year else currentMonth.year - 1
    val endYear = if(currentMonth.monthValue >= 9) currentMonth.year + 1 else currentMonth.year

    return Pair(YearMonth.of(startYear, 9), YearMonth.of(endYear,8))
}
