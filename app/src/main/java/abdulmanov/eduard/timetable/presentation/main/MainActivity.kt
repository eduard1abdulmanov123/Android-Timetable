package abdulmanov.eduard.timetable.presentation.main

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.ActivityMainBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.base.BaseActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val navigator =  AppNavigator(this, R.id.mainFragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null){
            router.replaceScreen(Screens.timetable())
        }
    }

    companion object{
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}