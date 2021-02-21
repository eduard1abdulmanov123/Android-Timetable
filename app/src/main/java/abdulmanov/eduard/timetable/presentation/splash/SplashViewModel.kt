package abdulmanov.eduard.timetable.presentation.splash

import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import io.reactivex.Single
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val router: Router,
    private val authInteractor: AuthInteractor,
    private val timetableInteractor: TimetableInteractor
): BaseViewModel() {

    fun executeTransitionProcessing() {
        val user = authInteractor.getUser()

        if (user.token != null && user.currentTimetableId != null) {
            Single.fromCallable {
               Thread.sleep(1000)
            }.safeSubscribe(
                {
                    router.replaceScreen(Screens.main())
                },
                {}
            )
        } else {
            router.replaceScreen(Screens.login())
        }
    }
}