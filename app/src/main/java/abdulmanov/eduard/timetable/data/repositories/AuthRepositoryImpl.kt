package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.database.AppDatabase
import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.remote.models.UserNetModel
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.domain.models.User
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import io.reactivex.Completable
import io.reactivex.Single

class AuthRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val database: AppDatabase,
    private val authSharedPreferences: AuthSharedPreferences,
    private val timetableSharedPreferences: TimetableSharedPreferences
): AuthRepository {

    override fun signUp(login: String, password: String): Single<User> {
        return timetableApi.signUp(UserNetModel.Request(login, password))
            .map(UserNetModel::toDomain)
            .doOnSuccess(::saveUser)
    }

    override fun signIn(login: String, password: String): Single<User> {
        return timetableApi.signIn(UserNetModel.Request(login, password))
            .map(UserNetModel::toDomain)
            .doOnSuccess(::saveUser)
    }

    override fun logout(): Completable {
        return Completable.fromCallable {
            authSharedPreferences.clearAll()
            timetableSharedPreferences.clearAll()
            database.clearAll()
        }
    }

    override fun saveUser(user: User) {
        authSharedPreferences.token = user.token
        authSharedPreferences.refreshToken = user.refreshToken
        authSharedPreferences.userName = user.userName
        authSharedPreferences.currentTimetableId = user.currentTimetableId
    }

    override fun getUser(): User {
        return User(
            userName = authSharedPreferences.userName,
            token = authSharedPreferences.token,
            refreshToken = authSharedPreferences.refreshToken,
            currentTimetableId = authSharedPreferences.currentTimetableId
        )
    }

    override fun haveAccess(): Boolean {
        return authSharedPreferences.userName == timetableSharedPreferences.creatorUsername
    }
}