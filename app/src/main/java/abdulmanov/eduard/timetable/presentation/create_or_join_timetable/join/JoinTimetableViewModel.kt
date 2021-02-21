package abdulmanov.eduard.timetable.presentation.create_or_join_timetable.join

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
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

    fun openScreenCreateTimetable() = router.replaceScreen(Screens.createTimetable())

    fun joinTimetable(link: String){
        if(link.isEmpty()){
            _showMessageEvent.value = stringProvider.getString(R.string.join_timetable_error_empty_link)
        }else if(_showApplyProgress.value == false){
            timetableInteractor.joinTimetable(link)
                .addDispatchers()
                .doOnSubscribe { _showApplyProgress.value = true }
                .doOnTerminate { _showApplyProgress.value = false }
                .subscribe(
                    {
                        router.newRootScreen(Screens.main())
                    },
                    {
                        _showMessageEvent.value = it.message.toString()
                    }
                )
                .connect()
        }
    }
}