package abdulmanov.eduard.timetable.presentation.splash

import abdulmanov.eduard.timetable.data.local.sharedpreferences.FcmSharedPreferences
import abdulmanov.eduard.timetable.databinding.ActivitySplashBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var fcmSharedPreferences: FcmSharedPreferences

    override val navigator = AppNavigator(this, -1)

    private val viewModel by initViewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel.showMessageEvent.observe(this, Observer(::showMessageAsToast))

        if(savedInstanceState == null) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if(it.isSuccessful){
                    fcmSharedPreferences.fcmToken = it.result
                }
                viewModel.executeTransitionProcessing()
            }
        }
    }
}