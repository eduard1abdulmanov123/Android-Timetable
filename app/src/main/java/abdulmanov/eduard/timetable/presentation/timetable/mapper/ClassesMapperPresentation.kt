package abdulmanov.eduard.timetable.presentation.timetable.mapper

import abdulmanov.eduard.timetable.domain.models.Classes
import abdulmanov.eduard.timetable.domain.models.MultipleClass
import abdulmanov.eduard.timetable.presentation.events.multipleclass.models.MultipleClassPresentationModel
import javax.inject.Inject

class ClassesMapperPresentation @Inject constructor() {

    fun mapClassesToPresentationModels(classes: Classes): List<Any> {
        return mapMultipleClassesToPresentationModels(classes.multipleClasses)
    }

    private fun mapMultipleClassesToPresentationModels(multipleClasses: List<MultipleClass>): List<MultipleClassPresentationModel>{
        return multipleClasses.map {
            MultipleClassPresentationModel(
                id = it.id,
                nameSubject = it.nameSubject,
                nameTeacher = it.nameTeacher,
                audience = it.audience,
                typeClass = it.typeClass,
                startOfClass = it.startOfClass,
                endOfClass = it.endOfClass,
                dayOfWeek = it.dayOfWeek.toString(),
                periodicity = it.periodicity.toString()
            )
        }
    }
}