package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.Timetable
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Single
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round

class TimetableRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val sharedPreferences: TimetableSharedPreferences
): TimetableRepository {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d")
    private var timetable: Timetable? = null

    override fun createTimetable(typeWeek: TypeWeek): Single<Timetable> {
        return timetableApi.createTimetable(TimetableNetModel.Request(typeWeek.number))
            .map(TimetableNetModel::toDomain)
    }

    override fun joinTimetable(link: String): Single<Timetable> {
        return timetableApi.joinTimetable(link)
            .map(TimetableNetModel::toDomain)
    }

    override fun getTimetable(refresh: Boolean): Single<Timetable> {
        return if(refresh) {
            timetableApi.getTimetable()
                .map(TimetableNetModel::toDomain)
                .doOnSuccess {
                    saveTimetableInfo(it)
                    timetable = it
                }
        }else{
            Single.fromCallable { timetable }
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
            dateUpdate = sharedPreferences.dateUpdate ?: ""
        )
    }

    override fun getTimetableLink(): String {
        return "${TimetableApi.BASE_URL}${sharedPreferences.link}"
    }

    override fun getTypeWeekForDate(date: LocalDate): TypeWeek {
        var startTypeWeek = sharedPreferences.typeWeek
        var startUpdateDate = LocalDate.parse(sharedPreferences.dateUpdate!!, dateFormatter)

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