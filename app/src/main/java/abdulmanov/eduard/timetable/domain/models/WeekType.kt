package abdulmanov.eduard.timetable.domain.models

enum class WeekType(val number: Int) {
    EVEN(2), ODD(1);
}

fun Int.numberToWeekNumber(): WeekType {
    return when(this){
        2 -> WeekType.EVEN
        1 -> WeekType.ODD
        else -> WeekType.EVEN
    }
}