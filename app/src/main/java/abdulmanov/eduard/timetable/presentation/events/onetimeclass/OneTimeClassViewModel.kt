package abdulmanov.eduard.timetable.presentation.events.onetimeclass

import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class OneTimeClassViewModel @Inject constructor(
    private val router: Router
): BaseViewModel() {

    fun onBackCommandClick() = router.exit()
}