package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.User
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable
import io.reactivex.Single

class AuthInteractor(
    private val authRepository: AuthRepository,
    private val timetableRepository: TimetableRepository,
) {

    fun signUp(login: String, password: String): Completable {
        return authRepository.signUp(login, password)
            .doOnSuccess { authRepository.saveUser(it) }
            .ignoreElement()
    }

    fun signIn(login: String, password: String): Single<User> {
        return authRepository.signIn(login, password)
            .doOnSuccess { authRepository.saveUser(it) }
            .flatMap { user ->
                if(user.currentTimetableId != null) {
                    timetableRepository.getTimetable()
                        .doOnSuccess { timetableRepository.saveTimetableInfo(it) }
                        .flatMap { Single.fromCallable { user } }
                }else{
                    Single.fromCallable { user }
                }
            }
    }

    fun getUser(): User {
        return authRepository.getUser()
    }
}