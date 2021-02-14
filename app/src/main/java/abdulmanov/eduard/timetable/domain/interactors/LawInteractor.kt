package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository

class LawInteractor(
    private val authRepository: AuthRepository,
    private val timetableRepository: TimetableRepository
) {

    fun haveAccess():Boolean{
        return authRepository.getUser().userName == timetableRepository.getTimetableInfo().creatorUsername
    }
}