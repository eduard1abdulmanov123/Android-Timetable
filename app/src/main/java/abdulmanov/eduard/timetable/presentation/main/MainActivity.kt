package abdulmanov.eduard.timetable.presentation.main

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

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val navigator =  AppNavigator(this, R.id.mainFragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            router.replaceScreen(Screens.timetable())
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

    companion object{
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}