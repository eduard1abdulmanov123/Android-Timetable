package abdulmanov.eduard.timetable.presentation.create_or_join_timetable

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.ActivityCreateOrJoinTimetableBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.base.BaseActivity
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class CreateOrJoinTimetableActivity : BaseActivity<ActivityCreateOrJoinTimetableBinding>() {

    override val navigator =  AppNavigator(this, R.id.onboardingFragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        if(intent.dataString != null){
            router.replaceScreen(Screens.joinTimetable(intent.dataString!!))
        }else if(savedInstanceState == null){
            router.replaceScreen(Screens.createTimetable())
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CreateOrJoinTimetableActivity::class.java)
    }
}