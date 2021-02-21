package abdulmanov.eduard.timetable.presentation.events.multipleclass.models

import abdulmanov.eduard.timetable.domain.models.MultipleClass
import abdulmanov.eduard.timetable.domain.models.Periodicity
import abdulmanov.eduard.timetable.presentation._common.extensions.getFullTitleDayOfWeekForNumber
import abdulmanov.eduard.timetable.presentation._common.extensions.getNumberForFullTitleDayOfWeek
import abdulmanov.eduard.timetable.presentation.events.common.ItemToBeSortedByTime
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
    var periodicity: String = ""
): Parcelable, ItemToBeSortedByTime {

    override val timeToSort get() =  startOfClass

    fun isNew(): Boolean = id == -1

    companion object {

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
                periodicity = Periodicity.fromNumber(multipleClass.periodicity)
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
                periodicity = Periodicity.fromString(multipleClass.periodicity)
            )
        }
    }
}