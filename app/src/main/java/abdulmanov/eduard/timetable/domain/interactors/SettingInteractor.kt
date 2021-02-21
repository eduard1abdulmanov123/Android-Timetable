package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.FeedbackData
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.SettingRepository
import io.reactivex.Completable
import java.time.LocalDate

class SettingInteractor(private val settingRepository: SettingRepository) {

    fun setTypeWeek(typeWeek: TypeWeek): Completable {
        return settingRepository.setTypeWeek(typeWeek)
    }

    fun getTypeWeek(date: LocalDate): TypeWeek {
        return settingRepository.getTypeWeek(date)
    }

    fun clearTimetable(): Completable {
        return settingRepository.clearTimetable()
    }

    fun getTimetableLink(): String {
        return settingRepository.getTimetableLink()
    }

    fun getFeedbackData(): FeedbackData {
        return settingRepository.getFeedbackData()
    }

    fun getUserName(): String {
        return settingRepository.getUserName()
    }

    fun logout(): Completable {
        return settingRepository.logout()
    }
}