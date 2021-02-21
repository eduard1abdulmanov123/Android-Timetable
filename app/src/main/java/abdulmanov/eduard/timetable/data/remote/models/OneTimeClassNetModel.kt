package abdulmanov.eduard.timetable.data.remote.models

import abdulmanov.eduard.timetable.data.local.database.models.OneTimeClassDbModel
import abdulmanov.eduard.timetable.domain.models.OneTimeClass
import com.google.gson.annotations.SerializedName

class OneTimeClassNetModel {

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
        @SerializedName("dateOfClass")
        val dateOfClass: String
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
        @SerializedName("dateOfClass")
        val dateOfClass: String
    )

    companion object {

        fun toDatabase(oneTimeClass: Response): OneTimeClassDbModel {
            return OneTimeClassDbModel(
                id = oneTimeClass.id,
                nameSubject = oneTimeClass.nameSubject,
                nameTeacher = oneTimeClass.nameTeacher,
                audience = oneTimeClass.audience,
                typeClass = oneTimeClass.typeClass,
                startOfClass = oneTimeClass.startOfClass,
                endOfClass = oneTimeClass.endOfClass,
                dateOfClass = oneTimeClass.dateOfClass
            )
        }

        fun toDomain(oneTimeClass: Response): OneTimeClass {
            return OneTimeClass(
                id = oneTimeClass.id,
                nameSubject = oneTimeClass.nameSubject,
                nameTeacher = oneTimeClass.nameTeacher,
                audience = oneTimeClass.audience,
                typeClass = oneTimeClass.typeClass,
                startOfClass = oneTimeClass.startOfClass,
                endOfClass = oneTimeClass.endOfClass,
                dateOfClass = oneTimeClass.dateOfClass
            )
        }

        fun fromDomain(oneTimeClass: OneTimeClass): Request {
            return Request(
                nameSubject = oneTimeClass.nameSubject,
                nameTeacher = oneTimeClass.nameTeacher,
                audience = oneTimeClass.audience,
                typeClass = oneTimeClass.typeClass,
                startOfClass = oneTimeClass.startOfClass,
                endOfClass = oneTimeClass.endOfClass,
                dateOfClass = oneTimeClass.dateOfClass
            )
        }
    }
}