package abdulmanov.eduard.timetable.presentation.splash

import abdulmanov.eduard.timetable.databinding.ActivitySplashBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.github.terrakok.cicerone.NavigatorHolder

import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, -1)

    private val viewModel by initViewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel.showMessageEvent.observe(this, Observer(::showMessage))

        if(savedInstanceState == null) {
            viewModel.executeTransitionProcessing()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}