package abdulmanov.eduard.timetable.data.remote.models

import abdulmanov.eduard.timetable.domain.models.User
import com.google.gson.annotations.SerializedName

class UserNetModel {

    data class Response(
        @SerializedName("username")
        val userName: String,
        @SerializedName("token")
        val token: String,
        @SerializedName("refreshToken")
        val refreshToken: String,
        @SerializedName("currentTimetableId")
        val currentTimetableId: Int?
    )

    data class Request(
        @SerializedName("username")
        val login: String,
        @SerializedName("password")
        val password: String
    )

    companion object {

        fun toDomain(user: Response): User {
            return User(
                userName = user.userName,
                token = user.token,
                refreshToken = user.refreshToken,
                currentTimetableId = user.currentTimetableId
            )
        }
    }
}