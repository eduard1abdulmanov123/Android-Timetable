package abdulmanov.eduard.timetable.presentation.splash

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.domain.interactors.SettingInteractor
import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.extensions.getStatus
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import android.util.Log
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    override val router: Router,
    override val authInteractor: AuthInteractor,
    private val stringProvider: StringProvider,
    private val timetableInteractor: TimetableInteractor,
): BaseViewModel() {

    fun executeTransitionProcessing() {
        val user = authInteractor.getUser()

        if (user.token != null && user.currentTimetableId != null) {
            timetableInteractor.fetchTimetableAndNote()
                .addDispatchers()
                .subscribe(
                    {
                        router.replaceScreen(Screens.main())
                    },
                    {
                        onError(it){
                            router.replaceScreen(Screens.main())
                            _showMessageEvent.value = stringProvider.getString(R.string.splash_error)
                        }
                    }
                )
                .connect()
        } else {
            router.replaceScreen(Screens.login())
        }
    }
}