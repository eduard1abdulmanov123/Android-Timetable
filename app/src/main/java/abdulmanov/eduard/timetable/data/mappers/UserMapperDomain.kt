package abdulmanov.eduard.timetable.data.mappers

import abdulmanov.eduard.timetable.data.remote.models.UserNetModel
import abdulmanov.eduard.timetable.domain.models.User

class UserMapperDomain {

    fun userNetModelToDomainModel(user: UserNetModel.Response): User{
        return User(
            userName = user.userName,
            token = user.token,
            currentTimetableId = user.currentTimetableId
        )
    }
}