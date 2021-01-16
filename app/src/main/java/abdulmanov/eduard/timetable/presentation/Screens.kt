package abdulmanov.eduard.timetable.presentation

import abdulmanov.eduard.timetable.presentation.login.LoginActivity
import abdulmanov.eduard.timetable.presentation.login.sign_in.SignInFragment
import abdulmanov.eduard.timetable.presentation.login.sign_up.SignUpFragment
import abdulmanov.eduard.timetable.presentation.main.MainActivity
import abdulmanov.eduard.timetable.presentation.timetable.TimetableFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun main() = ActivityScreen{
        MainActivity.newIntent(it)
    }

    fun login() = ActivityScreen{
        LoginActivity.newIntent(it)
    }

    fun signUp() = FragmentScreen{
        SignUpFragment.newInstance()
    }

    fun signIn() = FragmentScreen{
        SignInFragment.newInstance()
    }

    fun timetable() = FragmentScreen{
        TimetableFragment.newInstance()
    }
}