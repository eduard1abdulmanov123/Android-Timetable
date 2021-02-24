package abdulmanov.eduard.timetable.data.remote.models

import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel
import abdulmanov.eduard.timetable.domain.models.MultipleClass
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
        @SerializedName("startOfClass")
        val startOfClass: String,
        @SerializedName("endOfClass")
        val endOfClass: String,
        @SerializedName("dayOfWeek")
        val dayOfWeek: Int,
        @SerializedName("periodicity")
        val periodicity: Int,
        @SerializedName("canceledClasses")
        val canceledClasses: String
    )

    data class Request(
        @SerializedName("nameSubject")
        val nameSubject: String,
        @SerializedName("nameTeacher")
        val nameTeacher: String,
        @SerializedName("audience")
        val audience: String,
        @SerializedName("typeClass")
        val typeClass: String,
        @SerializedName("startOfClass")
        val startOfClass: String,
        @SerializedName("endOfClass")
        val endOfClass: String,
        @SerializedName("dayOfWeek")
        val dayOfWeek: Int,
        @SerializedName("periodicity")
        val periodicity: Int,
        @SerializedName("canceledClasses")
        val canceledClasses: String
    )

    companion object {

        fun toDatabase(multipleClasses: List<Response>): List<MultipleClassDbModel> {
            return multipleClasses.map(::toDatabase)
        }

        fun toDatabase(multipleClass: Response): MultipleClassDbModel {
            return MultipleClassDbModel(
                id = multipleClass.id,
                nameSubject = multipleClass.nameSubject,
                nameTeacher = multipleClass.nameTeacher,
                audience = multipleClass.audience,
                typeClass = multipleClass.typeClass,
                startOfClass = multipleClass.startOfClass,
                endOfClass = multipleClass.endOfClass,
                dayOfWeek = multipleClass.dayOfWeek,
                periodicity = multipleClass.periodicity,
                canceledClasses = multipleClass.canceledClasses
            )
        }

        fun fromDomain(multipleClass: MultipleClass): Request {
            return Request(
                nameSubject = multipleClass.nameSubject,
                nameTeacher = multipleClass.nameTeacher,
                audience = multipleClass.audience,
                typeClass = multipleClass.typeClass,
                startOfClass = multipleClass.startOfClass,
                endOfClass = multipleClass.endOfClass,
                dayOfWeek = multipleClass.dayOfWeek,
                periodicity = multipleClass.periodicity,
                canceledClasses = multipleClass.canceledClasses
            )
        }
    }
}