package abdulmanov.eduard.timetable.presentation.login

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.ActivityLoginBinding
import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.base.BaseActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val navigator = AppNavigator(this, R.id.loginFragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            router.replaceScreen(Screens.signIn())
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}