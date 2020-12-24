package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import io.reactivex.Completable

class AuthInteractor(private val authRepository: AuthRepository) {

    fun signUp(login: String, password: String): Completable {
        return authRepository.signUp(login, password)
            .flatMapCompletable { authRepository.saveToken(it.token) }
    }

    fun signIn(login: String, password: String): Completable {
        return authRepository.signIn(login, password)
            .flatMapCompletable { authRepository.saveToken(it.token) }
    }
}