package abdulmanov.eduard.timetable.presentation.events.onetimeclass.models

import abdulmanov.eduard.timetable.domain.models.OneTimeClass
import abdulmanov.eduard.timetable.presentation.events.common.ItemToBeSortedByTime
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
): Parcelable, ItemToBeSortedByTime {

    override val timeToSort get() = startOfClass

    fun isNew(): Boolean = id == -1

    companion object {

        private val DATE_FORMATTER_PRESENTER = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")

        fun fromDomain(oneTimeClasses: List<OneTimeClass>): List<OneTimeClassPresentationModel> {
            return oneTimeClasses.map(::fromDomain)
        }

        fun fromDomain(oneTimeClass: OneTimeClass): OneTimeClassPresentationModel {
            return OneTimeClassPresentationModel(
                id = oneTimeClass.id,
                nameSubject = oneTimeClass.nameSubject,
                nameTeacher = oneTimeClass.nameTeacher,
                audience = oneTimeClass.audience,
                typeClass = oneTimeClass.typeClass,
                startOfClass = oneTimeClass.startOfClass,
                endOfClass = oneTimeClass.endOfClass,
                dateOfClass = DATE_FORMATTER_PRESENTER.format(LocalDate.parse(oneTimeClass.dateOfClass))
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
                dateOfClass = LocalDate.parse(oneTimeClass.dateOfClass, DATE_FORMATTER_PRESENTER).toString()
            )
        }
    }
}