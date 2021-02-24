package abdulmanov.eduard.timetable.presentation.events.multipleclass.models

import abdulmanov.eduard.timetable.domain.models.MultipleClass
import abdulmanov.eduard.timetable.domain.models.Periodicity
import abdulmanov.eduard.timetable.presentation._common.extensions.getFullTitleDayOfWeekForNumber
import abdulmanov.eduard.timetable.presentation._common.extensions.getNumberForFullTitleDayOfWeek
import abdulmanov.eduard.timetable.presentation.events.common.ItemToBeSortedByTime
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.models.OneTimeClassPresentationModel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class MultipleClassPresentationModel(
    val id: Int = -1,
    var nameSubject: String = "",
    var nameTeacher: String = "",
    var audience: String = "",
    var typeClass: String = "",
    var startOfClass: String = "",
    var endOfClass: String = "",
    var dayOfWeek: String = "",
    var periodicity: String = "",
    var canceledClasses: String = ""
): Parcelable, ItemToBeSortedByTime {

    override val timeToSort get() =  startOfClass

    fun isNew(): Boolean = id == -1

    companion object {

        private val DATE_FORMATTER_PRESENTER = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")

        fun fromDomain(multipleClasses: List<MultipleClass>): List<MultipleClassPresentationModel>{
            return multipleClasses.map(::fromDomain)
        }

        fun fromDomain(multipleClass: MultipleClass): MultipleClassPresentationModel {
            return MultipleClassPresentationModel(
                id = multipleClass.id,
                nameSubject = multipleClass.nameSubject,
                nameTeacher = multipleClass.nameTeacher,
                audience = multipleClass.audience,
                typeClass = multipleClass.typeClass,
                startOfClass = multipleClass.startOfClass,
                endOfClass = multipleClass.endOfClass,
                dayOfWeek = getFullTitleDayOfWeekForNumber(multipleClass.dayOfWeek),
                periodicity = Periodicity.fromNumber(multipleClass.periodicity),
                canceledClasses = multipleClass.canceledClasses.ifNotEmpty {
                    split(";").joinToString("\n", "", "") {
                        DATE_FORMATTER_PRESENTER.format(LocalDate.parse(it))
                    }
                }
            )
        }

        fun toDomain(multipleClass: MultipleClassPresentationModel): MultipleClass {
            return MultipleClass(
                id = multipleClass.id,
                nameSubject = multipleClass.nameSubject,
                nameTeacher = multipleClass.nameTeacher,
                audience = multipleClass.audience,
                typeClass = multipleClass.typeClass,
                startOfClass = multipleClass.startOfClass,
                endOfClass = multipleClass.endOfClass,
                dayOfWeek = getNumberForFullTitleDayOfWeek(multipleClass.dayOfWeek),
                periodicity = Periodicity.fromString(multipleClass.periodicity),
                canceledClasses = multipleClass.canceledClasses.ifNotEmpty {
                    split("\n").joinToString(";", "", "") {
                        LocalDate.parse(it, DATE_FORMATTER_PRESENTER).toString()
                    }
                }
            )
        }

        private fun String.ifNotEmpty(block: String.() -> String): String {
            return if(this.isEmpty()) "" else block(this)
        }
    }
}