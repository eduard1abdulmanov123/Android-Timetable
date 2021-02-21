package abdulmanov.eduard.timetable.presentation.login.sign_in

import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val router: Router,
    private val authInteractor: AuthInteractor
): BaseViewModel(){

    private val _showLoginInApp = MutableLiveData(false)
    val showLoginInApp: LiveData<Boolean>
        get() = _showLoginInApp

    fun openSignUp() = router.replaceScreen(Screens.signUp())

    @Suppress("UnstableApiUsage")
    fun signIn(login: String, password: String){
        if(_showLoginInApp.value == false) {

            authInteractor.signIn(login, password)
                .addDispatchers()
                .doOnSubscribe { _showLoginInApp.value = true }
                .doOnTerminate { _showLoginInApp.value = false }
                .subscribe(
                    {
                        if(it.currentTimetableId != null){
                            router.replaceScreen(Screens.main())
                        }else{
                            router.replaceScreen(Screens.createOrJoinTimetable())
                        }
                    },
                    {
                        _showMessageEvent.value = it.message.toString()
                    }
                )
                .connect()
        }
    }
}