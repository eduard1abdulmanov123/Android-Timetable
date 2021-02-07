package abdulmanov.eduard.timetable.domain.models

enum class TypeWeek(val number: Int) {
    EVEN(2), ODD(1);
}

fun Int.numberToTypeWeek(): TypeWeek {
    return when(this){
        2 -> TypeWeek.EVEN
        1 -> TypeWeek.ODD
        else -> TypeWeek.EVEN
    }
}

fun TypeWeek.switch(): TypeWeek {
    return when(this){
        TypeWeek.EVEN -> TypeWeek.ODD
        TypeWeek.ODD -> TypeWeek.EVEN
    }
}