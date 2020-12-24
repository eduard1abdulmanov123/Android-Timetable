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
        val user = UserNetModel.Request(login, password)

        return timetableApi.signUp(user).map {
            User(
                userName = it.userName,
                token = it.token
            )
        }
    }

    override fun signIn(login: String, password: String): Single<User> {
        val user = UserNetModel.Request(login, password)

        return timetableApi.signIn(user).map {
            User(
                userName = it.userName,
                token = it.token
            )
        }
    }

    override fun saveToken(token: String): Completable {
        return Completable.fromCallable {
            sharedPreferences.setToken(token)
        }
    }
}