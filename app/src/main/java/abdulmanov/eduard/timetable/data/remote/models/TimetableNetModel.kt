package abdulmanov.eduard.timetable.data.remote.models

import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import com.google.gson.annotations.SerializedName

class TimetableNetModel{

    data class Request(
        @SerializedName("typeWeek")
        val typeWeek: Int,
        @SerializedName("timeZone")
        val timeZone: String = ""
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
        @SerializedName("timeZone")
        val timeZone: String,
        @SerializedName("multipleClasses")
        val multipleClasses: List<MultipleClassNetModel.Response>,
        @SerializedName("oneTimeClasses")
        val oneTimeClasses: List<OneTimeClassNetModel.Response>
    )

    companion object {

        fun toDomain(timetable: Response): Timetable {
            return Timetable(
                id = timetable.id,
                creatorUsername = timetable.creatorUsername,
                link = timetable.link,
                dateUpdate = timetable.dateUpdate,
                typeWeek = TypeWeek.numberToTypeWeek(timetable.typeWeek),
                timeZone = timetable.timeZone
            )
        }
    }
}