package abdulmanov.eduard.timetable.presentation.create_or_join_timetable.create

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CreateTimetableViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val timetableInteractor: TimetableInteractor
): BaseViewModel() {

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    var currentSelectTypeWeek: TypeWeek? = null

    fun openScreenJoinTimetable() = router.replaceScreen(Screens.joinTimetable())

    fun createTimetable(){
        if(currentSelectTypeWeek == null){
            _showMessageEvent.value = stringProvider.getString(R.string.create_timetable_error_select_type_week)
        }else if(_showApplyProgress.value == false){
            timetableInteractor.createTimetable(currentSelectTypeWeek!!)
                .addDispatchers()
                .doOnSubscribe { _showApplyProgress.value = true }
                .doOnTerminate { _showApplyProgress.value = false }
                .subscribe(
                    {
                        router.newRootChain(Screens.main())
                    },
                    {
                        _showMessageEvent.value = it.message.toString()
                    }
                )
                .connect()
        }
    }
}