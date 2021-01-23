package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.remote.models.UserNetModel
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.domain.models.User
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import io.reactivex.Completable
import io.reactivex.Single

class AuthRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val sharedPreferences: AuthSharedPreferences
): AuthRepository {

    override fun signUp(login: String, password: String): Single<User> {
        return timetableApi.signUp(UserNetModel.Request(login, password))
            .map { User(it.userName, it.token, it.currentTimetableId) }
    }

    override fun signIn(login: String, password: String): Single<User> {
        return timetableApi.signIn(UserNetModel.Request(login, password))
            .map { User(it.userName, it.token, it.currentTimetableId) }
    }

    override fun saveUser(user: User) {
        sharedPreferences.setToken(user.token)
        sharedPreferences.setUserName(user.userName)
        sharedPreferences.setCurrentTimetableId(user.currentTimetableId)
    }

    override fun getUser(): User {
        return User(
            userName = sharedPreferences.getUserName(),
            token = sharedPreferences.getToken(),
            currentTimetableId = sharedPreferences.getCurrentTimetableId()
        )
    }
}