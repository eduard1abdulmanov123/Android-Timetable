package abdulmanov.eduard.timetable.presentation.events.onetimeclass.models

import abdulmanov.eduard.timetable.domain.models.OneTimeClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneTimeClassPresentationModel(
    val id: Int = -1,
    var nameSubject: String = "",
    var nameTeacher: String = "",
    var audience: String = "",
    var typeClass: String = "",
    var startOfClass: String = "",
    var endOfClass: String = "",
    var dateOfClass: String = ""
): Parcelable {

    fun isNew(): Boolean = id == -1

    companion object {

        fun fromDomain(oneTimeClass: OneTimeClass): OneTimeClassPresentationModel {
            return OneTimeClassPresentationModel(
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

        fun toDomain(oneTimeClass: OneTimeClassPresentationModel): OneTimeClass {
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
    }
}