package abdulmanov.eduard.timetable.domain.models

import java.lang.IllegalStateException
import java.time.LocalDate
import kotlin.math.round

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

        fun getTypeWeekForDate(date: LocalDate, typeWeek: TypeWeek, updateDate: String): TypeWeek {
            var startTypeWeek = typeWeek
            var startUpdateDate = LocalDate.parse(updateDate)

            while (round(startUpdateDate.dayOfYear.toDouble()/7) != round(date.dayOfYear.toDouble()/7)){
                startUpdateDate = startUpdateDate.plusWeeks(1)
                startTypeWeek = switch(startTypeWeek)
            }

            return startTypeWeek
        }

        private fun switch(typeWeek: TypeWeek): TypeWeek {
            return when(typeWeek){
                EVEN -> ODD
                ODD -> EVEN
            }
        }
    }
}