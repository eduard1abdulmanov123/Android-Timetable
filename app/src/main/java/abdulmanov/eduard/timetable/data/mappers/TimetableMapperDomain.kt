package abdulmanov.eduard.timetable.data.mappers

import abdulmanov.eduard.timetable.data.remote.models.MultipleClassNetModel
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek

class TimetableMapperDomain() {

    fun timetableNetModelToDomainModel(timetable: TimetableNetModel.Response): Timetable {
        return Timetable(
            id = timetable.id,
            creatorUsername = timetable.creatorUsername,
            link = timetable.link,
            dateUpdate = timetable.dateUpdate,
            typeWeek = TypeWeek.numberToTypeWeek(timetable.typeWeek),
            multipleClasses = timetable.multipleClasses.map { MultipleClassNetModel.toDomain(it) }
        )
    }
}