package abdulmanov.eduard.timetable.presentation.login.sign_in

import abdulmanov.eduard.timetable.presentation.Screens
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val router: Router
): ViewModel(){

    fun openSignUp() = router.replaceScreen(Screens.signUp())
}