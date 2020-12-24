package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.User
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {

    fun signUp(login: String, password: String): Single<User>

    fun signIn(login: String, password: String): Single<User>

    fun saveToken(token: String): Completable
}