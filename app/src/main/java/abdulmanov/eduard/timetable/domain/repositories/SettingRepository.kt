package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.FeedbackData
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import io.reactivex.Completable
import java.time.LocalDate

interface SettingRepository {

    fun setTypeWeek(typeWeek: TypeWeek): Completable

    fun getTypeWeek(date: LocalDate): TypeWeek

    fun clearTimetable(): Completable

    fun getTimetableLink(): String

    fun getFeedbackData(): FeedbackData

    fun getUserName(): String
}