package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.BuildConfig
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.domain.models.FeedbackData
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.SettingRepository
import android.os.Build
import io.reactivex.Completable

class SettingRepositoryImpl(
    private val timetableApi: TimetableApi
): SettingRepository {

    override fun setTypeWeek(typeWeek: TypeWeek): Completable {
        return timetableApi.changeTypeWeekTheTimetable(TimetableNetModel.Request(typeWeek.number))
    }

    override fun getFeedbackData(): FeedbackData {
        return FeedbackData(Build.VERSION.SDK_INT, Build.MODEL, BuildConfig.VERSION_NAME)
    }
}