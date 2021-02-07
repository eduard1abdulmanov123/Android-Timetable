package abdulmanov.eduard.timetable.data.remote.models

import com.google.gson.annotations.SerializedName

class TimetableNetModel{

    data class Request(
        @SerializedName("typeWeek")
        val typeWeek: Int
    )

    data class Response(
        @SerializedName("id")
        val id: Int,
        @SerializedName("creatorUsername")
        val creatorUsername: String,
        @SerializedName("link")
        val link: String,
        @SerializedName("typeWeek")
        val typeWeek: Int,
        @SerializedName("dateUpdate")
        val dateUpdate: String,
        @SerializedName("multipleClasses")
        val multipleClasses: List<MultipleClassNetModel.Response>
    )
}