package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.database.dao.MultipleClassDao
import abdulmanov.eduard.timetable.data.local.database.dao.OneTimeClassDao
import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel
import abdulmanov.eduard.timetable.data.local.database.models.OneTimeClassDbModel
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
import kotlin.math.round

class TimetableRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val multipleClassDao: MultipleClassDao,
    private val oneTimeClassDao: OneTimeClassDao,
    private val sharedPreferences: TimetableSharedPreferences
): TimetableRepository {

    override fun createTimetable(typeWeek: TypeWeek): Single<Timetable> {
        return timetableApi.createTimetable(TimetableNetModel.Request(typeWeek.number))
            .map(TimetableNetModel::toDomain)
    }

    override fun joinTimetable(link: String): Single<Timetable> {
        return timetableApi.joinTimetable(link)
            .map(TimetableNetModel::toDomain)
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
                    id = sharedPreferences.id,
                    creatorUsername = sharedPreferences.creatorUsername ?: "",
                    link = sharedPreferences.link ?: "",
                    typeWeek = sharedPreferences.typeWeek,
                    dateUpdate = sharedPreferences.dateUpdate ?: "",
                    multipleClasses = MultipleClassDbModel.toDomain(multipleClasses),
                    oneTimeClasses = OneTimeClassDbModel.toDomain(oneTimeClasses)
                )
            }
        )
    }

    override fun saveTimetableInfo(timetable: Timetable) {
        sharedPreferences.id = timetable.id
        sharedPreferences.creatorUsername = timetable.creatorUsername
        sharedPreferences.link = timetable.link
        sharedPreferences.typeWeek = timetable.typeWeek
        sharedPreferences.dateUpdate = timetable.dateUpdate
    }

    override fun getTimetableInfo(): Timetable {
        return Timetable(
            id = sharedPreferences.id,
            creatorUsername = sharedPreferences.creatorUsername ?: "",
            link = sharedPreferences.link ?: "",
            typeWeek = sharedPreferences.typeWeek,
            dateUpdate = sharedPreferences.dateUpdate ?: ""
        )
    }

    override fun getTimetableLink(): String {
        return "${TimetableApi.BASE_URL}${sharedPreferences.link}"
    }

    override fun getTypeWeekForDate(date: LocalDate): TypeWeek {
        var startTypeWeek = sharedPreferences.typeWeek
        var startUpdateDate = LocalDate.parse(sharedPreferences.dateUpdate!!)

        while (round(startUpdateDate.dayOfYear.toDouble()/7) != round(date.dayOfYear.toDouble()/7)){
            startUpdateDate = startUpdateDate.plusWeeks(1)
            startTypeWeek = TypeWeek.switch(startTypeWeek)
        }

        return startTypeWeek
    }

    override fun clearAllInformationAboutTimetable() {
        sharedPreferences.clearAll()
    }
}