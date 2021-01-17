package abdulmanov.eduard.timetable.presentation.multipleclass

import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MultipleClassViewModel @Inject constructor(
    private val router: Router
): BaseViewModel() {

    fun onBackCommandClick() = router.exit()
}