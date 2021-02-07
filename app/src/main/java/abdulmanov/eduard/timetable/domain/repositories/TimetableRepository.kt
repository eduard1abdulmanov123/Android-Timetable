package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.Classes
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.reflect.Type
import java.time.LocalDate

interface TimetableRepository {

    fun createTimetable(typeWeek: TypeWeek): Single<Timetable>

    fun joinTimetable(link: String): Single<Timetable>

    fun getTimetable(): Single<Timetable>

    fun getTimetableLink(): String

    fun getTypeWeekForDate(date: LocalDate): TypeWeek

    fun getClassesForSelectedDate(refresh: Boolean, date: LocalDate): Single<Classes>

    fun saveTimetableInfo(timetable: Timetable)

    fun getTimetableInfo(): Timetable

    fun clearAllInformationAboutTimetable()
}