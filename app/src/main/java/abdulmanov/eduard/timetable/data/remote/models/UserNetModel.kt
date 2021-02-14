package abdulmanov.eduard.timetable.data.remote.models

import com.google.gson.annotations.SerializedName

class UserNetModel {

    data class Response(
        @SerializedName("username")
        val userName: String,
        @SerializedName("token")
        val token: String,
        @SerializedName("currentTimetableId")
        val currentTimetableId: Int?
    )

    data class Request(
        @SerializedName("username")
        val login: String,
        @SerializedName("password")
        val password: String
    )
}