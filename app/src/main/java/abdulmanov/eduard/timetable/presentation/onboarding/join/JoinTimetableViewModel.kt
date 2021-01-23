package abdulmanov.eduard.timetable.presentation.onboarding.join

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import javax.inject.Inject

class JoinTimetableViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val timetableInteractor: TimetableInteractor
): BaseViewModel() {

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    private val _showMessageErrorEvent = LiveEvent<String>()
    val showMessageErrorEvent: LiveData<String>
        get() = _showMessageErrorEvent

    fun openScreenCreateTimetable() = router.replaceScreen(Screens.createTimetable())

    fun joinTimetable(link: String){
        if(link.isEmpty()){
            _showMessageErrorEvent.value = stringProvider.getString(R.string.join_timetable_error_empty_link)
        }else if(_showApplyProgress.value == false){
            _showApplyProgress.value = true

            timetableInteractor.joinTimetable(link).safeSubscribe(
                {
                    _showApplyProgress.value = false
                    router.replaceScreen(Screens.main())
                },
                {
                    _showApplyProgress.value = false
                    _showMessageErrorEvent.value = it.message.toString()
                }
            )
        }
    }
}