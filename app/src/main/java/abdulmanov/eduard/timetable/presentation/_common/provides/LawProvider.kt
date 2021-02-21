package abdulmanov.eduard.timetable.presentation._common.provides

import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import android.view.View
import androidx.core.view.isVisible
import javax.inject.Inject

class LawProvider @Inject constructor(
    private val authInteractor: AuthInteractor
){

    fun haveAccess() = authInteractor.haveAccess()

    fun showIfYouHaveLaw(view: View) {
        view.isVisible = authInteractor.haveAccess()
    }
}