package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.mappers.TimetableMapperDomain
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.WeekType
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable
import io.reactivex.Single

class TimetableRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val timetableMapperDomain: TimetableMapperDomain,
    private val authSharedPreferences: AuthSharedPreferences,
    private val timetableSharedPreferences: TimetableSharedPreferences
): TimetableRepository {

    override fun createTimetable(weekType: WeekType): Single<Timetable> {
        return timetableApi.createTimetable(TimetableNetModel.Request(weekType.number))
            .map(timetableMapperDomain::timetableNetModelToDomainModel)
    }

    override fun joinTimetable(link: String): Single<Timetable> {
        return timetableApi.joinTimetable(link)
            .map(timetableMapperDomain::timetableNetModelToDomainModel)
    }

    override fun saveTimetableInfo(timetable: Timetable): Completable {
        return Completable.fromCallable{
            authSharedPreferences.setCurrentTimetableId(timetable.id)
            timetableSharedPreferences.setCreatorUsername(timetable.creatorUsername)
            timetableSharedPreferences.setLink(timetable.link)
            timetableSharedPreferences.setWeekType(timetable.weekType)
        }
    }
}