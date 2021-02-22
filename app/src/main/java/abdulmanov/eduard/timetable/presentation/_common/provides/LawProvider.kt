package abdulmanov.eduard.timetable.presentation._common.provides

import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.presentation.events.note.models.NotePresentationModel
import android.view.View
import androidx.core.view.isVisible
import javax.inject.Inject

class LawProvider @Inject constructor(
    private val authInteractor: AuthInteractor
){

    fun haveAccess() = authInteractor.haveAccess()
    
    fun haveAccessToNote(note: NotePresentationModel) = authInteractor.getUser().userName == note.username
    
    fun showIfYouHaveLaw(view: View) {
        view.isVisible = authInteractor.haveAccess()
    }
}