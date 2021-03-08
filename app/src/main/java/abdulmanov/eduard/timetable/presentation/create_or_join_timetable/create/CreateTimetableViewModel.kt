package abdulmanov.eduard.timetable.presentation.create_or_join_timetable.create

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
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
    override val router: Router,
    override val authInteractor: AuthInteractor,
    private val stringProvider: StringProvider,
    private val timetableInteractor: TimetableInteractor
): BaseViewModel() {

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    var currentSelectTypeWeek: TypeWeek? = null
    var currentSelectTimeZone: String? = null

    fun openScreenJoinTimetable() = router.replaceScreen(Screens.joinTimetable())

    fun createTimetable(){
        if(currentSelectTypeWeek == null || currentSelectTimeZone == null){
            _showMessageEvent.value = stringProvider.getString(R.string.create_timetable_error)
        }else if(_showApplyProgress.value == false){
            timetableInteractor.createTimetable(currentSelectTypeWeek!!, currentSelectTimeZone!!)
                .addDispatchers()
                .doOnSubscribe { _showApplyProgress.value = true }
                .doOnTerminate { _showApplyProgress.value = false }
                .subscribe(
                    {
                        router.newRootChain(Screens.main())
                    },
                    {
                        onError(it)
                    }
                )
                .connect()
        }
    }
}