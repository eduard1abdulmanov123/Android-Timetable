package abdulmanov.eduard.timetable.presentation.events.note

import abdulmanov.eduard.timetable.domain.interactors.NoteInteractor
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.timetable.presentation.events.note.mappers.NoteMapperPresentation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val router: Router,
    private val noteInteractor: NoteInteractor,
    private val mapper: NoteMapperPresentation
): BaseViewModel() {

    private val _showMessageErrorEvent = LiveEvent<String>()
    val showMessageErrorEvent: LiveData<String>
        get() = _showMessageErrorEvent

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    fun onBackCommandClick() = router.exit()

    fun createNote(content: String, date: String, time: String, visibility: Boolean){
        if(_showApplyProgress.value == false){
            val note = mapper.dataToNote(content, date, time, visibility)

            _showApplyProgress.value = true
            noteInteractor.createNote(note).safeSubscribe(
                {
                    _showApplyProgress.value = false
                    onBackCommandClick()
                },
                {
                    _showApplyProgress.value = false
                    _showMessageErrorEvent.value = it.message.toString()
                }
            )
        }
    }
}