package abdulmanov.eduard.timetable.data.remote.models

import com.google.gson.annotations.SerializedName

class TimetableNetModel{

    data class Request(
        @SerializedName("weekType")
        val weekType: Int
    )

    data class Response(
        @SerializedName("id")
        val id: Int,
        @SerializedName("creatorUsername")
        val creatorUsername: String,
        @SerializedName("link")
        val link: String,
        @SerializedName("weekType")
        val weekType: Int,
    )
}