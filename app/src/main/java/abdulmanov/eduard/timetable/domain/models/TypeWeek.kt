package abdulmanov.eduard.timetable.domain.models

import java.lang.IllegalStateException

enum class TypeWeek(val number: Int) {
    ODD(1),
    EVEN(2);

    companion object {

        fun numberToTypeWeek(number: Int): TypeWeek {
            return when(number){
                ODD.number -> ODD
                EVEN.number -> EVEN
                else -> throw IllegalStateException("number not found")
            }
        }

        fun switch(typeWeek: TypeWeek): TypeWeek {
            return when(typeWeek){
                EVEN -> ODD
                ODD -> EVEN
            }
        }
    }
}