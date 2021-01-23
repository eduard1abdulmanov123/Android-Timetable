package abdulmanov.eduard.timetable.data.mappers

import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.numberToWeekNumber

class TimetableMapperDomain {

    fun timetableNetModelToDomainModel(timetable: TimetableNetModel.Response): Timetable {
        return Timetable(
            id = timetable.id,
            creatorUsername = timetable.creatorUsername,
            link = timetable.link,
            weekType = timetable.weekType.numberToWeekNumber()
        )
    }
}