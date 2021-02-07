package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.Classes
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

class TimetableInteractor(
    private val authRepository: AuthRepository,
    private val timetableRepository: TimetableRepository
) {

    fun createTimetable(typeWeek: TypeWeek): Completable{
        return timetableRepository.createTimetable(typeWeek)
            .doOnSuccess {
                timetableRepository.saveTimetableInfo(it)
                attachUserToTimetable(it)
            }
            .ignoreElement()
    }

    fun joinTimetable(link: String): Completable {
        return timetableRepository.joinTimetable(link)
            .doOnSuccess {
                timetableRepository.saveTimetableInfo(it)
                attachUserToTimetable(it)
            }
            .ignoreElement()
    }

    fun getClassesForSelectedDate(refresh: Boolean, date: LocalDate): Single<Classes> {
        return timetableRepository.getClassesForSelectedDate(refresh, date)
    }

    private fun attachUserToTimetable(timetable: Timetable){
        val updatedUser = authRepository.getUser().copy(currentTimetableId = timetable.id)
        authRepository.saveUser(updatedUser)
    }
}