package abdulmanov.eduard.timetable.presentation.timetable

import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.domain.models.Classes
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.timetable.presentation.events.multipleclass.models.MultipleClassPresentationModel
import abdulmanov.eduard.timetable.presentation.events.note.models.NotePresentationModel
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.models.OneTimeClassPresentationModel
import abdulmanov.eduard.timetable.presentation.timetable.mapper.ClassesMapperPresentation
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import java.time.LocalDate
import javax.inject.Inject

class TimetableViewModel @Inject constructor(
    private val router: Router,
    private val timetableInteractor: TimetableInteractor,
    private val classesMapperPresentation: ClassesMapperPresentation
):BaseViewModel() {

    private val _classes = MutableLiveData<List<Any>>()
    val classes: LiveData<List<Any>>
        get() = _classes

    fun onBackCommandClick() = router.exit()

    fun openScreenSetting() = router.navigateTo(Screens.setting())

    fun openScreenMultipleClass(multipleClass: MultipleClassPresentationModel? = null) = router.navigateTo(Screens.multipleClass(multipleClass))

    fun openScreenOneTimeClass(oneTimeClass: OneTimeClassPresentationModel? = null) = router.navigateTo(Screens.oneTimeClass(oneTimeClass))

    fun openScreenNote(note: NotePresentationModel? = null) = router.navigateTo(Screens.note(note))

    fun getClassesForSelectedDate(date:LocalDate, refresh: Boolean = false){
        timetableInteractor.getClassesForSelectedDate(_classes.value==null || refresh, date)
            .map { it.multipleClasses.map { MultipleClassPresentationModel.fromDomain(it) } }
            .safeSubscribe(
                {
                    _classes.value = it
                },
                {
                    Log.d("FuckFuck", it.message.toString())
                    it.stackTrace.forEach {
                        Log.d("FuckFuck", it.toString())
                    }
                }
            )
    }
}