package abdulmanov.eduard.timetable.data.mappers

import abdulmanov.eduard.timetable.data.remote.models.MultipleClassNetModel
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.MultipleClass
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.numberToTypeWeek

class TimetableMapperDomain {

    fun timetableNetModelToDomainModel(timetable: TimetableNetModel.Response): Timetable {
        return Timetable(
            id = timetable.id,
            creatorUsername = timetable.creatorUsername,
            link = timetable.link,
            dateUpdate = timetable.dateUpdate,
            typeWeek = timetable.typeWeek.numberToTypeWeek(),
            multipleClasses = multipleClassesNetModelsToDomainModels(timetable.multipleClasses)
        )
    }

    private fun multipleClassesNetModelsToDomainModels(multipleClasses: List<MultipleClassNetModel.Response>): List<MultipleClass> {
        return multipleClasses.map {
            MultipleClass(
                id = it.id,
                nameSubject = it.nameSubject,
                nameTeacher = it.nameTeacher,
                audience = it.audience,
                typeClass = it.typeClass,
                color = it.color,
                startOfClass = it.startOfClass,
                endOfClass = it.endOfClass,
                dayOfWeek = it.dayOfWeek,
                periodicity = it.periodicity
            )
        }
    }
}