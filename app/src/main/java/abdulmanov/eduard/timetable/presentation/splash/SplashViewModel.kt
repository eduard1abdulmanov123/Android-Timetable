package abdulmanov.eduard.timetable.presentation.splash

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import androidx.lifecycle.LiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val authInteractor: AuthInteractor,
    private val timetableInteractor: TimetableInteractor
): BaseViewModel() {

    private val _showMessageEvent = LiveEvent<String>()
    val showMessageEvent: LiveData<String>
        get() = _showMessageEvent

    fun executeTransitionProcessing() {
        val user = authInteractor.getUser()

        if (user.token != null && user.currentTimetableId != null) {
            timetableInteractor.fetchTimetableAndNote()
                .safeSubscribe(
                    {
                        router.replaceScreen(Screens.main())
                    },
                    {
                        _showMessageEvent.value = stringProvider.getString(R.string.splash_error)
                        router.replaceScreen(Screens.main())
                    }
                )
        } else {
            router.replaceScreen(Screens.login())
        }
    }
}