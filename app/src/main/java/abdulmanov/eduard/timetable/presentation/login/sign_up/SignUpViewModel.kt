package abdulmanov.eduard.timetable.presentation.login.sign_up

import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val router: Router,
    private val authInteractor: AuthInteractor
): BaseViewModel(){

    private val _showMessageEvent = LiveEvent<Boolean>()
    val showMessageEvent: LiveData<Boolean>
        get() = _showMessageEvent

    private val _showRegistrationInApp = MutableLiveData(false)
    val showRegistrationInApp: LiveData<Boolean>
        get() = _showRegistrationInApp

    fun openSignIn() = router.replaceScreen(Screens.signIn())

    fun signUp(login: String, password: String){
        if(_showRegistrationInApp.value == false) {
            _showMessageEvent.value = false
            _showRegistrationInApp.value = true

            authInteractor.signUp(login, password).safeSubscribe(
                {
                    _showRegistrationInApp.value = false
                    router.replaceScreen(Screens.createOrJoinTimetable())
                },
                {
                    _showRegistrationInApp.value = false
                    _showMessageEvent.value = true
                }
            )
        }
    }
}