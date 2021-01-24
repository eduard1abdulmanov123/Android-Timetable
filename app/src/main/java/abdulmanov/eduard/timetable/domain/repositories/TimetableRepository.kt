package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import io.reactivex.Completable
import io.reactivex.Single

interface TimetableRepository {

    fun createTimetable(typeWeek: TypeWeek): Single<Timetable>

    fun joinTimetable(link: String): Single<Timetable>

    fun getTimetable(): Single<Timetable>

    fun saveTimetableInfo(timetable: Timetable)

    fun getTimetableInfo(): Timetable

    fun clearAllInformationAboutTimetable()
}