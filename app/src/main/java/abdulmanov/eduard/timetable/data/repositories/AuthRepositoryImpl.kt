package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.mappers.UserMapperDomain
import abdulmanov.eduard.timetable.data.remote.models.UserNetModel
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.domain.models.User
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import io.reactivex.Single

class AuthRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val userMapperDomain: UserMapperDomain,
    private val sharedPreferences: AuthSharedPreferences
): AuthRepository {

    override fun signUp(login: String, password: String): Single<User> {
        return timetableApi.signUp(UserNetModel.Request(login, password))
            .map(userMapperDomain::userNetModelToDomainModel)
    }

    override fun signIn(login: String, password: String): Single<User> {
        return timetableApi.signIn(UserNetModel.Request(login, password))
            .map(userMapperDomain::userNetModelToDomainModel)
    }

    override fun saveUser(user: User) {
        sharedPreferences.token = user.token
        sharedPreferences.userName = user.userName
        sharedPreferences.currentTimetableId = user.currentTimetableId
    }

    override fun getUser(): User {
        return User(
            userName = sharedPreferences.userName,
            token = sharedPreferences.token,
            currentTimetableId = sharedPreferences.currentTimetableId
        )
    }

    override fun clearAllInformationAboutUser() {
        sharedPreferences.clearAll()
    }
}