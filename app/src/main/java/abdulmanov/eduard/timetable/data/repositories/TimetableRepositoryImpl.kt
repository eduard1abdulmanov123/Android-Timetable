package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.mappers.TimetableMapperDomain
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Single

class TimetableRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val timetableMapperDomain: TimetableMapperDomain,
    private val sharedPreferences: TimetableSharedPreferences
): TimetableRepository {

    override fun createTimetable(typeWeek: TypeWeek): Single<Timetable> {
        return timetableApi.createTimetable(TimetableNetModel.Request(typeWeek.number))
            .map(timetableMapperDomain::timetableNetModelToDomainModel)
    }

    override fun joinTimetable(link: String): Single<Timetable> {
        return timetableApi.joinTimetable(link)
            .map(timetableMapperDomain::timetableNetModelToDomainModel)
    }

    override fun getTimetable(): Single<Timetable> {
        return timetableApi.getTimetable()
            .map(timetableMapperDomain::timetableNetModelToDomainModel)
    }

    override fun saveTimetableInfo(timetable: Timetable) {
        sharedPreferences.creatorUsername = timetable.creatorUsername
        sharedPreferences.link = timetable.link
        sharedPreferences.typeWeek = timetable.typeWeek
        sharedPreferences.dateUpdate = timetable.dateUpdate
    }

    override fun getTimetableInfo(): Timetable {
        return Timetable(
            creatorUsername = sharedPreferences.creatorUsername ?: "",
            link = sharedPreferences.link ?: "",
            typeWeek = sharedPreferences.typeWeek,
            dateUpdate = sharedPreferences.dateUpdate
        )
    }

    override fun clearAllInformationAboutTimetable() {
        sharedPreferences.clearAll()
    }
}