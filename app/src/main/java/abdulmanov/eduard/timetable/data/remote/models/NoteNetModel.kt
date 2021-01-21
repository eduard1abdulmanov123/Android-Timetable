package abdulmanov.eduard.timetable.data.remote.models

import com.google.gson.annotations.SerializedName

class NoteNetModel {

    data class Request(
        @SerializedName("content")
        val content: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("time")
        val time: String,
        @SerializedName("visibility")
        val visibility: Boolean
    )

    data class Response(
        @SerializedName("id")
        val id: Int,
        @SerializedName("content")
        val content: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("time")
        val time: String,
        @SerializedName("visibility")
        val visibility: Boolean
    )
}