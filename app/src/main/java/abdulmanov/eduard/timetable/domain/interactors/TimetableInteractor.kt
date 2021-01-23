package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.WeekType
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable

class TimetableInteractor(
    private val timetableRepository: TimetableRepository
) {

    fun createTimetable(weekType: WeekType): Completable{
        return timetableRepository.createTimetable(weekType)
            .flatMapCompletable { timetableRepository.saveTimetableInfo(it) }
    }

    fun joinTimetable(link: String): Completable {
        return timetableRepository.joinTimetable(link)
            .flatMapCompletable { timetableRepository.saveTimetableInfo(it) }
    }

}