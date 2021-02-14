package abdulmanov.eduard.timetable.data.remote.models

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
        val periodicity: Int
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
        val periodicity: Int
    )

    companion object {

        fun toDomain(multipleClass: Response): MultipleClass {
            return MultipleClass(
                id = multipleClass.id,
                nameSubject = multipleClass.nameSubject,
                nameTeacher = multipleClass.nameTeacher,
                audience = multipleClass.audience,
                typeClass = multipleClass.typeClass,
                startOfClass = multipleClass.startOfClass,
                endOfClass = multipleClass.endOfClass,
                dayOfWeek = multipleClass.dayOfWeek,
                periodicity = multipleClass.periodicity
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
                periodicity = multipleClass.periodicity
            )
        }
    }
}