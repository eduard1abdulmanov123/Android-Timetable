package abdulmanov.eduard.timetable.presentation.timetable

import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class TimetableViewModel @Inject constructor(
    private val router: Router
):BaseViewModel() {

    fun onBackCommandClick() = router.exit()

    fun openScreenSetting() = router.navigateTo(Screens.setting())

    fun openScreenMultipleClass() = router.navigateTo(Screens.multipleClass())

    fun openScreenOneTimeClass() = router.navigateTo(Screens.oneTimeClass())

    fun openScreenNote() = router.navigateTo(Screens.note())
}