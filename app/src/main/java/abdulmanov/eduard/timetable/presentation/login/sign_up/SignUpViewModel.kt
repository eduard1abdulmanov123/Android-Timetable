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
    override val router: Router,
    override val authInteractor: AuthInteractor
): BaseViewModel(){

    private val _showRegistrationInApp = MutableLiveData(false)
    val showRegistrationInApp: LiveData<Boolean>
        get() = _showRegistrationInApp

    fun openSignIn() = router.replaceScreen(Screens.signIn())

    fun signUp(login: String, password: String){
        if(_showRegistrationInApp.value == false) {

            authInteractor.signUp(login, password)
                .addDispatchers()
                .doOnSubscribe {  _showRegistrationInApp.value = true }
                .doOnTerminate { _showRegistrationInApp.value = false }
                .subscribe(
                    {
                        router.replaceScreen(Screens.createOrJoinTimetable())
                    },
                    {
                        onError(it)
                    }
                )
                .connect()
        }
    }
}