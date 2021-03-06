package abdulmanov.eduard.timetable.dagger.components

import abdulmanov.eduard.timetable.dagger.modules.*
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
import abdulmanov.eduard.timetable.presentation.notification.MyFirebaseMessagingService
import abdulmanov.eduard.timetable.presentation.setting.SettingFragment
import abdulmanov.eduard.timetable.presentation.splash.SplashActivity
import abdulmanov.eduard.timetable.presentation.timetable.TimetableFragment
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class,
        ViewModelModule::class,
        SharedPreferencesModule::class,
        NetworkModule::class,
        DataModule::class,
        DomainModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent{

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(myFirebaseMessagingService: MyFirebaseMessagingService)

    fun inject(splashActivity: SplashActivity)

    fun inject(loginActivity: LoginActivity)

    fun inject(signInFragment: SignInFragment)

    fun inject(signUpFragment: SignUpFragment)

    fun inject(createOrJoinTimetableActivity: CreateOrJoinTimetableActivity)

    fun inject(createTimetableFragment: CreateTimetableFragment)

    fun inject(jointTimetableFragment: JoinTimetableFragment)

    fun inject(mainActivity: MainActivity)

    fun inject(timetableFragment: TimetableFragment)

    fun inject(settingFragment: SettingFragment)

    fun inject(multipleClassFragment: MultipleClassFragment)

    fun inject(oneTimeClassFragment: OneTimeClassFragment)

    fun inject(noteFragment: NoteFragment)
}