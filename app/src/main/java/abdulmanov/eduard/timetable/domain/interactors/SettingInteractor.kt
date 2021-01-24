package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.FeedbackData
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.SettingRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable

class SettingInteractor(
    private val authRepository: AuthRepository,
    private val timetableRepository: TimetableRepository,
    private val settingRepository: SettingRepository
) {

    fun setTypeWeek(typeWeek: TypeWeek): Completable {
        return settingRepository.setTypeWeek(typeWeek)
            .doOnComplete {
                val oldTimetableInfo = timetableRepository.getTimetableInfo()
                val updatedTimetableInfo = oldTimetableInfo.copy(typeWeek = typeWeek)
                timetableRepository.saveTimetableInfo(updatedTimetableInfo)
            }
    }

    fun getTypeWeek(): TypeWeek {
        return timetableRepository.getTimetableInfo().typeWeek
    }

    fun getFeedbackData(): FeedbackData {
        return settingRepository.getFeedbackData()
    }

    fun getUserName(): String {
        return authRepository.getUser().userName ?: ""
    }

    fun logout() {
        authRepository.clearAllInformationAboutUser()
        timetableRepository.clearAllInformationAboutTimetable()
    }
}