package abdulmanov.eduard.timetable.presentation.create_or_join_timetable

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation.Screens
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class CreateOrJoinTimetableActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val navigator =  AppNavigator(this, R.id.onboardingFragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_join_timetable)

        if(savedInstanceState == null){
            router.replaceScreen(Screens.createTimetable())
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

    companion object {
        fun newIntent(context: Context) = Intent(context, CreateOrJoinTimetableActivity::class.java)
    }
}