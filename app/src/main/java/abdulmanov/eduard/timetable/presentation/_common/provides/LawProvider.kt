package abdulmanov.eduard.timetable.presentation._common.provides

import abdulmanov.eduard.timetable.domain.interactors.LawInteractor
import android.view.View
import androidx.core.view.isVisible
import javax.inject.Inject

class LawProvider @Inject constructor(private val lawInteractor: LawInteractor){

    fun showIfYouHaveLaw(view: View) {
        view.isVisible = lawInteractor.haveAccess()
    }
}