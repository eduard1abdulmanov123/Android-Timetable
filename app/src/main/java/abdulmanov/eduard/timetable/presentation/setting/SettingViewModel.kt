package abdulmanov.eduard.timetable.presentation.setting

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.SettingInteractor
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import androidx.lifecycle.LiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import java.time.LocalDate
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val settingInteractor: SettingInteractor
): BaseViewModel() {

    private val _changeTypeWeekEvent = LiveEvent<TypeWeek>()
    val changeTypeWeekEvent: LiveData<TypeWeek>
        get() = _changeTypeWeekEvent

    private val _showMessageEvent = LiveEvent<String>()
    val showMessageEvent: LiveData<String>
        get() = _showMessageEvent

    fun onBackCommandClick() = router.exit()

    fun openScreenCreateOrJoinTimetable() = router.navigateTo(Screens.createTimetable())

    fun setCurrentSelectTypeWeek(typeWeek: TypeWeek){
        settingInteractor.setTypeWeek(typeWeek).safeSubscribe(
            {
                _showMessageEvent.value = stringProvider.getString(R.string.setting_type_week_success)
            },
            {
                _changeTypeWeekEvent.value = settingInteractor.getTypeWeek(LocalDate.now())
                _showMessageEvent.value = stringProvider.getString(R.string.setting_type_week_error)
            }
        )
    }

    fun getCurrentSelectTypeWeek(): TypeWeek{
        return settingInteractor.getTypeWeek(LocalDate.now())
    }

    fun onOpenScreenSendLink(){
        val link = settingInteractor.getTimetableLink()
        router.navigateTo(Screens.sendLink(link))
    }

    fun onOpenScreenFeedback() {
        val feedbackData = settingInteractor.getFeedbackData()
        router.navigateTo(Screens.feedback(feedbackData))
    }

    fun onOpenScreenLogin() {
        settingInteractor.logout()
        router.replaceScreen(Screens.login())
    }

    fun getUserName(): String {
        return settingInteractor.getUserName()
    }
}