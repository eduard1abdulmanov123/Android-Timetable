package abdulmanov.eduard.timetable.presentation

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.models.FeedbackData
import abdulmanov.eduard.timetable.presentation.login.LoginActivity
import abdulmanov.eduard.timetable.presentation.login.sign_in.SignInFragment
import abdulmanov.eduard.timetable.presentation.login.sign_up.SignUpFragment
import abdulmanov.eduard.timetable.presentation.main.MainActivity
import abdulmanov.eduard.timetable.presentation.events.multipleclass.MultipleClassFragment
import abdulmanov.eduard.timetable.presentation.events.note.NoteFragment
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.OneTimeClassFragment
import abdulmanov.eduard.timetable.presentation.create_or_join_timetable.CreateOrJoinTimetableActivity
import abdulmanov.eduard.timetable.presentation.create_or_join_timetable.create.CreateTimetableFragment
import abdulmanov.eduard.timetable.presentation.create_or_join_timetable.join.JoinTimetableFragment
import abdulmanov.eduard.timetable.presentation.events.multipleclass.models.MultipleClassPresentationModel
import abdulmanov.eduard.timetable.presentation.setting.SettingFragment
import abdulmanov.eduard.timetable.presentation.timetable.TimetableFragment
import android.content.Intent
import android.net.Uri
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

    fun createOrJoinTimetable() = ActivityScreen{
        CreateOrJoinTimetableActivity.newIntent(it)
    }

    fun createTimetable() = FragmentScreen{
        CreateTimetableFragment.newInstance()
    }

    fun joinTimetable(link: String = "") = FragmentScreen{
        JoinTimetableFragment.newInstance(link)
    }

    fun timetable() = FragmentScreen{
        TimetableFragment.newInstance()
    }

    fun setting() = FragmentScreen{
        SettingFragment.newInstance()
    }

    fun feedback(data: FeedbackData) = ActivityScreen{
        val addresses = listOf(it.getString(R.string.feedback_address)).toTypedArray()
        val subject = it.getString(R.string.feedback_subject)
        val body = it.getString(R.string.feedback_body, data.androidVersion, data.modelPhone, data.applicationVersion)

        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        Intent.createChooser(intent, "Email:")
    }

    fun sendLink(url: String) = ActivityScreen{
        Intent(Intent.ACTION_SEND).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
    }

    fun multipleClass(multipleClass: MultipleClassPresentationModel?) = FragmentScreen{
        MultipleClassFragment.newInstance(multipleClass)
    }

    fun oneTimeClass() = FragmentScreen{
        OneTimeClassFragment.newInstance()
    }

    fun note() = FragmentScreen{
        NoteFragment.newInstance()
    }
}