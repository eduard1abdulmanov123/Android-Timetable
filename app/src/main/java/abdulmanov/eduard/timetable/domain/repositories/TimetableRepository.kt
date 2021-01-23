package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.WeekType
import io.reactivex.Completable
import io.reactivex.Single

interface TimetableRepository {

    fun createTimetable(weekType: WeekType): Single<Timetable>

    fun joinTimetable(link: String): Single<Timetable>

    fun saveTimetableInfo(timetable: Timetable): Completable
}