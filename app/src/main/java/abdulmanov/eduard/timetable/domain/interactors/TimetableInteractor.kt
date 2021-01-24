package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable

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

    private fun attachUserToTimetable(timetable: Timetable){
        val oldUser = authRepository.getUser()
        val updatedUser = oldUser.copy(currentTimetableId = timetable.id)
        authRepository.saveUser(updatedUser)
    }
}