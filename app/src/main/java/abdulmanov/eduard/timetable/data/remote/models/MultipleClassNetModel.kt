package abdulmanov.eduard.timetable.data.remote.models

import com.google.gson.annotations.SerializedName

class MultipleClassNetModel {

    data class Response(
        @SerializedName("id")
        val id: Int,
        @SerializedName("nameSubject")
        val nameSubject: String,
        @SerializedName("nameTeacher")
        val nameTeacher: String,
        @SerializedName("audience")
        val audience: String,
        @SerializedName("typeClass")
        val typeClass: String,
        @SerializedName("color")
        val color: String,
        @SerializedName("startOfClass")
        val startOfClass: String,
        @SerializedName("endOfClass")
        val endOfClass: String,
        @SerializedName("dayOfWeek")
        val dayOfWeek: Int,
        @SerializedName("periodicity")
        val periodicity: Int
    )
}