package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.User
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable
import io.reactivex.Single

class AuthInteractor(
    private val authRepository: AuthRepository,
    private val timetableInteractor: TimetableInteractor
) {

    fun signUp(login: String, password: String): Completable {
        return authRepository.signUp(login, password)
            .ignoreElement()
    }

    fun signIn(login: String, password: String): Single<User> {
        return authRepository.signIn(login, password)
            .flatMap { user ->
                if (user.currentTimetableId != null) {
                    timetableInteractor.fetchTimetableAndNote()
                        .andThen(Single.just(user))
                } else {
                    Single.fromCallable { user }
                }
            }
    }

    fun getUser(): User {
        return authRepository.getUser()
    }

    fun haveAccess():Boolean{
        return authRepository.haveAccess()
    }
}