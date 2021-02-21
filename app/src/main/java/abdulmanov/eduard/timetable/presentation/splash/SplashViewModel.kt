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
    private val router: Router,
    private val stringProvider: StringProvider,
    private val authInteractor: AuthInteractor,
    private val timetableInteractor: TimetableInteractor,
    private val settingInteractor: SettingInteractor,
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
                        if(it.getStatus() == "user_is_not_connect"){
                            logout()
                        }else {
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

    private fun logout(){
        settingInteractor.logout()
            .addDispatchers()
            .subscribe { router.replaceScreen(Screens.login()) }
            .connect()
    }
}