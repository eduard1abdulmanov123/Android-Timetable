package abdulmanov.eduard.timetable.presentation.login.sign_in

import abdulmanov.eduard.timetable.presentation.Screens
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val router: Router
): ViewModel(){

    private val _showMessageErrorEvent = LiveEvent<Boolean>()
    val showMessageErrorEvent: LiveData<Boolean>
        get() = _showMessageErrorEvent

    fun openSignUp() = router.replaceScreen(Screens.signUp())

    fun signIn(login: String, password: String){
        Log.d("FuckFuck","login = $login, password = $password")
    }
}