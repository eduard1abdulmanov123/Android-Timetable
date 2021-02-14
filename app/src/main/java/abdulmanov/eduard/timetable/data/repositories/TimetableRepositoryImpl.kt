package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.mappers.TimetableMapperDomain
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.Classes
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Single
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TimetableRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val timetableMapperDomain: TimetableMapperDomain,
    private val sharedPreferences: TimetableSharedPreferences
): TimetableRepository {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d")
    private var timetable: Timetable? = null

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

    override fun getTimetableLink(): String {
        return "${TimetableApi.BASE_URL}${sharedPreferences.link}"
    }

    override fun getTypeWeekForDate(date: LocalDate): TypeWeek {
        var startTypeWeek = sharedPreferences.typeWeek
        var startUpdateDate = LocalDate.parse(sharedPreferences.dateUpdate!!, dateFormatter)

        while (startUpdateDate.dayOfYear/7 != date.dayOfYear/7){
            startUpdateDate = startUpdateDate.plusWeeks(1)
            startTypeWeek = TypeWeek.switch(startTypeWeek)
        }

        return startTypeWeek
    }

    override fun getClassesForSelectedDate(refresh: Boolean, date: LocalDate): Single<Classes> {
        return if(refresh){
            timetableApi.getTimetable()
                .map(timetableMapperDomain::timetableNetModelToDomainModel)
                .doOnSuccess { timetable = it }
                .map{ pullOutClassesForSelectedDate(date) }
        }else{
            Single.fromCallable {
                pullOutClassesForSelectedDate(date)
            }
        }
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
            dateUpdate = sharedPreferences.dateUpdate ?: "",
            multipleClasses = listOf()
        )
    }

    override fun clearAllInformationAboutTimetable() {
        sharedPreferences.clearAll()
    }

    private fun pullOutClassesForSelectedDate(date: LocalDate): Classes {
        val multipleClasses = timetable!!.multipleClasses
            .filter { (it.periodicity == 0 || it.periodicity == timetable!!.typeWeek.number) && (it.dayOfWeek == date.dayOfWeek.value) }

        return Classes(multipleClasses)
    }
}