package abdulmanov.eduard.timetable.presentation.events.note

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.NoteInteractor
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.timetable.presentation.events.note.models.NotePresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import io.reactivex.Completable
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val noteInteractor: NoteInteractor
): BaseViewModel() {

    private val _showMessageEvent = LiveEvent<String>()
    val showMessageEvent: LiveData<String>
        get() = _showMessageEvent

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    lateinit var note: NotePresentationModel
        private set

    fun onBackCommandClick() = router.exit()

    fun setNoteOrDefault(note: NotePresentationModel?) {
        this.note = note ?: NotePresentationModel()
    }

    fun getTitleForToolbar(): String{
        return stringProvider.getString(if(note.isNew()) R.string.note_new_note else R.string.note_edit_note)
    }

    fun getTextForApplyButton(): String {
        return stringProvider.getString(if(note.isNew()) R.string.note_create else R.string.note_save)
    }

    fun createOrUpdate() {
        if(_showApplyProgress.value == false){
            getCompletableCreateOrUpdate()
                .addDispatchers()
                .doOnSubscribe { _showApplyProgress.value = true }
                .doOnTerminate { _showApplyProgress.value = false }
                .subscribe(
                    {
                        onBackCommandClick()
                    },
                    {
                        _showMessageEvent.value = it.message.toString()
                    }
                )
                .connect()
        }
    }

    fun delete() {
        noteInteractor.deleteNote(note.id)
            .safeSubscribe(
                {
                    onBackCommandClick()
                },
                {
                    _showMessageEvent.value = it.message.toString()
                }
            )
    }

    private fun getCompletableCreateOrUpdate(): Completable {
        val domainModel = NotePresentationModel.toDomain(note)

        return if(note.isNew()){
            noteInteractor.createNote(domainModel)
        }else{
            noteInteractor.updateNote(domainModel.id, domainModel)
        }
    }
}