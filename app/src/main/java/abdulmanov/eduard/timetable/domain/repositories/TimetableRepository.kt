package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

interface TimetableRepository {

    fun createTimetable(typeWeek: TypeWeek, timeZone: String): Completable

    fun joinTimetable(link: String): Completable

    fun fetchTimetable(): Completable

    fun getTimetable(): Single<Timetable>

    fun saveTimetableInfo(timetable: Timetable)

    fun getTypeWeekForDate(date: LocalDate): TypeWeek
}