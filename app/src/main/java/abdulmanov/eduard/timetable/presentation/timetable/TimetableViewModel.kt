package abdulmanov.eduard.timetable.presentation.timetable

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.timetable.presentation.events.multipleclass.models.MultipleClassPresentationModel
import abdulmanov.eduard.timetable.presentation.events.note.models.NotePresentationModel
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.models.OneTimeClassPresentationModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import java.sql.Time
import java.time.LocalDate
import javax.inject.Inject

class TimetableViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val timetableInteractor: TimetableInteractor
):BaseViewModel() {

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate>
        get() = _selectedDate

    private val _isCollapse = MutableLiveData<Boolean>()
    val isCollapse: LiveData<Boolean>
        get() = _isCollapse

    private val _state = MutableLiveData<TimetableState>()
    val state: LiveData<TimetableState>
        get() = _state

    private val _showMessageEvent = LiveEvent<String>()
    val showMessageEvent: LiveData<String>
        get() = _showMessageEvent

    fun onBackCommandClick() = router.exit()

    fun openScreenSetting() = router.navigateTo(Screens.setting())

    fun openScreenMultipleClass(multipleClass: MultipleClassPresentationModel? = null) = router.navigateTo(Screens.multipleClass(multipleClass))

    fun openScreenOneTimeClass(oneTimeClass: OneTimeClassPresentationModel? = null) = router.navigateTo(Screens.oneTimeClass(oneTimeClass))

    fun openScreenNote(note: NotePresentationModel? = null) = router.navigateTo(Screens.note(note))

    fun setSelectedDate(selectedDate: LocalDate){
        if(selectedDate != _selectedDate.value) {
            getClassesForSelectedDate(selectedDate, true)
        }
        _selectedDate.value = selectedDate

    }

    fun setIsCollapse(isCollapse: Boolean) {
        _isCollapse.value = isCollapse
    }

    fun getTypeWeekForDate(date: LocalDate): String {
        return when(timetableInteractor.getTypeWeekForDate(date)){
            TypeWeek.ODD -> stringProvider.getString(R.string.timetable_type_week_odd)
            TypeWeek.EVEN -> stringProvider.getString(R.string.timetable_type_week_even)
        }
    }

    fun getClassesForSelectedDate(date:LocalDate, refresh: Boolean = false){
        _state.value = TimetableState.Progress

        timetableInteractor.getTimetableForSelectedDate(refresh, date)
            .map { timetableWithNotes ->
                val multipleClasses = timetableWithNotes.timetable.multipleClasses.map { MultipleClassPresentationModel.fromDomain(it) }
                val oneTimeClasses = timetableWithNotes.timetable.oneTimeClasses.map { OneTimeClassPresentationModel.fromDomain(it) }
                val notes = timetableWithNotes.notes.map { NotePresentationModel.fromDomain(it) }
                multipleClasses + oneTimeClasses + notes
            }
            .safeSubscribe(
                {
                    if(it.isEmpty()){
                        _state.value = TimetableState.Empty
                    }else{
                        _state.value = TimetableState.Data(it)
                    }
                },
                {
                    _state.value = TimetableState.Error
                    _showMessageEvent.value = it.message.toString()
                }
            )
    }

    fun refresh(){
        _state.value = TimetableState.Refresh(_state.value!!)

        timetableInteractor.getTimetableForSelectedDate(true, _selectedDate.value!!)
            .map { timetableWithNotes ->
                val multipleClasses = timetableWithNotes.timetable.multipleClasses.map { MultipleClassPresentationModel.fromDomain(it) }
                val oneTimeClasses = timetableWithNotes.timetable.oneTimeClasses.map { OneTimeClassPresentationModel.fromDomain(it) }
                val notes = timetableWithNotes.notes.map { NotePresentationModel.fromDomain(it) }
                multipleClasses + oneTimeClasses + notes
            }
            .safeSubscribe(
                {
                    if(it.isEmpty()){
                        _state.value = TimetableState.Empty
                    }else{
                        _state.value = TimetableState.Data(it)
                    }
                },
                {
                    _state.value = (_state.value as TimetableState.Refresh).lastState
                    _showMessageEvent.value = it.message.toString()
                }
            )
    }
}