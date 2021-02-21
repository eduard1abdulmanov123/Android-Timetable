package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.User
import io.reactivex.Single

interface AuthRepository {

    fun signUp(login: String, password: String): Single<User>

    fun signIn(login: String, password: String): Single<User>

    fun saveUser(user: User)

    fun getUser(): User

    fun haveAccess(): Boolean
}