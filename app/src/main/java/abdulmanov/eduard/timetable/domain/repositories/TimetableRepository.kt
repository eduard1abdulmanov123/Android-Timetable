package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import io.reactivex.Single
import java.time.LocalDate

interface TimetableRepository {

    fun createTimetable(typeWeek: TypeWeek): Single<Timetable>

    fun joinTimetable(link: String): Single<Timetable>

    fun getTimetable(refresh: Boolean): Single<Timetable>

    fun saveTimetableInfo(timetable: Timetable)

    fun getTimetableInfo(): Timetable

    fun getTimetableLink(): String

    fun getTypeWeekForDate(date: LocalDate): TypeWeek

    fun clearAllInformationAboutTimetable()
}