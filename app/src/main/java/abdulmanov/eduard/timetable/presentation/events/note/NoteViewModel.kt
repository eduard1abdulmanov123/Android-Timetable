package abdulmanov.eduard.timetable.presentation.events.note

import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val router: Router
): BaseViewModel() {

    fun onBackCommandClick() = router.exit()
}