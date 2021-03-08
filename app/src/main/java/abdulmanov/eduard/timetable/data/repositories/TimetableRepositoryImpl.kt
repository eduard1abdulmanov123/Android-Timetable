package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.database.dao.MultipleClassDao
import abdulmanov.eduard.timetable.data.local.database.dao.OneTimeClassDao
import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel
import abdulmanov.eduard.timetable.data.local.database.models.OneTimeClassDbModel
import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.MultipleClassNetModel
import abdulmanov.eduard.timetable.data.remote.models.OneTimeClassNetModel
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

class TimetableRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val multipleClassDao: MultipleClassDao,
    private val oneTimeClassDao: OneTimeClassDao,
    private val authSharedPreferences: AuthSharedPreferences,
    private val timetableSharedPreferences: TimetableSharedPreferences
): TimetableRepository {

    override fun createTimetable(typeWeek: TypeWeek, timeZone: String): Completable {
        val netModel = TimetableNetModel.Request(typeWeek.number, timeZone)

        return timetableApi.createTimetable(netModel)
            .map(TimetableNetModel::toDomain)
            .doOnSuccess{
                saveTimetableInfo(it)
                authSharedPreferences.currentTimetableId = it.id
            }
            .ignoreElement()
    }

    override fun joinTimetable(link: String): Completable {
        return timetableApi.joinTimetable(link)
            .map(TimetableNetModel::toDomain)
            .doOnSuccess{
                saveTimetableInfo(it)
                authSharedPreferences.currentTimetableId = it.id
            }
            .ignoreElement()
    }

    override fun fetchTimetable(): Completable {
        return timetableApi.getTimetable()
            .doOnSuccess {
                val multipleClasses = MultipleClassNetModel.toDatabase(it.multipleClasses)
                val oneTimeClasses = OneTimeClassNetModel.toDatabase(it.oneTimeClasses)
                multipleClassDao.updateMultipleClasses(multipleClasses)
                oneTimeClassDao.updateOneTimeClasses(oneTimeClasses)
            }
            .map(TimetableNetModel::toDomain)
            .doOnSuccess(::saveTimetableInfo)
            .ignoreElement()
    }

    override fun getTimetable(): Single<Timetable> {
        return Single.zip(
            multipleClassDao.getMultipleClasses(),
            oneTimeClassDao.getOneTimeClasses(),
            { multipleClasses, oneTimeClasses ->
                Timetable(
                    id = timetableSharedPreferences.id,
                    creatorUsername = timetableSharedPreferences.creatorUsername ?: "",
                    link = timetableSharedPreferences.link ?: "",
                    typeWeek = timetableSharedPreferences.typeWeek,
                    timeZone = timetableSharedPreferences.timeZone ?: "",
                    dateUpdate = timetableSharedPreferences.dateUpdate ?: "",
                    multipleClasses = MultipleClassDbModel.toDomain(multipleClasses),
                    oneTimeClasses = OneTimeClassDbModel.toDomain(oneTimeClasses)
                )
            }
        )
    }

    override fun saveTimetableInfo(timetable: Timetable) {
        timetableSharedPreferences.id = timetable.id
        timetableSharedPreferences.creatorUsername = timetable.creatorUsername
        timetableSharedPreferences.link = timetable.link
        timetableSharedPreferences.typeWeek = timetable.typeWeek
        timetableSharedPreferences.dateUpdate = timetable.dateUpdate
        timetableSharedPreferences.timeZone = timetable.timeZone
    }

    override fun getTypeWeekForDate(date: LocalDate): TypeWeek {
        val typeWeek = timetableSharedPreferences.typeWeek
        val updateDate = timetableSharedPreferences.dateUpdate!!
        return TypeWeek.getTypeWeekForDate(date, typeWeek, updateDate)
    }
}