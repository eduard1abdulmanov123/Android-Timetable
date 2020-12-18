package abdulmanov.eduard.timetable.presentation.login.sign_up

import abdulmanov.eduard.timetable.presentation.Screens
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val router: Router
): ViewModel(){

    fun openSignIn() = router.replaceScreen(Screens.signIn())
}