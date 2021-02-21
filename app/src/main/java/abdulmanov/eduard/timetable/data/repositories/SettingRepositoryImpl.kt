package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.BuildConfig
import abdulmanov.eduard.timetable.data.local.database.AppDatabase
import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.FeedbackData
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.SettingRepository
import android.os.Build
import io.reactivex.Completable
import java.time.LocalDate

class SettingRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val database: AppDatabase,
    private val authSharedPreferences: AuthSharedPreferences,
    private val timetableSharedPreferences: TimetableSharedPreferences
): SettingRepository {

    override fun setTypeWeek(typeWeek: TypeWeek): Completable {
        return timetableApi.changeTypeWeekTheTimetable(TimetableNetModel.Request(typeWeek.number))
            .doOnSuccess {
                timetableSharedPreferences.typeWeek = typeWeek
                timetableSharedPreferences.dateUpdate = it.dateUpdate
            }
            .ignoreElement()
    }

    override fun getTypeWeek(date: LocalDate): TypeWeek {
        val typeWeek = timetableSharedPreferences.typeWeek
        val updateDate = timetableSharedPreferences.dateUpdate!!
        return TypeWeek.getTypeWeekForDate(date, typeWeek, updateDate)
    }

    override fun clearTimetable(): Completable {
        return timetableApi.clearTimetable()
            .doOnComplete {
                database.oneTimeClassDao.deleteAll()
                database.multipleClassDao.deleteAll()
                database.noteDao.deleteAllIsVisibility()
            }
    }

    override fun getTimetableLink(): String {
        return "${TimetableApi.BASE_URL}${timetableSharedPreferences.link}"
    }

    override fun getFeedbackData(): FeedbackData {
        return FeedbackData(Build.VERSION.SDK_INT, Build.MODEL, BuildConfig.VERSION_NAME)
    }

    override fun getUserName(): String {
        return authSharedPreferences.userName ?: ""
    }

    override fun logout(): Completable {
        return Completable.fromCallable {
            authSharedPreferences.clearAll()
            timetableSharedPreferences.clearAll()
            database.clearAll()
        }
    }
}