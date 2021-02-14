package abdulmanov.eduard.timetable.domain.models

enum class Periodicity(val number: Int, val title: String) {
    ALWAYS(0, "Каждую неделю"),
    ODD(1, "По нечетным неделям"),
    EVEN(2,"По четным неделям");

    companion object {

        fun fromString(value: String): Int {
            return when(value){
                ALWAYS.title -> ALWAYS.number
                EVEN.title -> EVEN.number
                ODD.title -> ODD.number
                else -> -1
            }
        }

        fun fromNumber(value: Int): String {
            return when(value){
                ALWAYS.number -> ALWAYS.title
                EVEN.number -> EVEN.title
                ODD.number -> ODD.title
                else -> ""
            }
        }
    }
}