package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.FeedbackData
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import io.reactivex.Completable

interface SettingRepository {

    fun setTypeWeek(typeWeek: TypeWeek): Completable

    fun getFeedbackData(): FeedbackData
}