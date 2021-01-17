package abdulmanov.eduard.timetable.dagger.components

import abdulmanov.eduard.timetable.dagger.modules.*
import abdulmanov.eduard.timetable.presentation.login.LoginActivity
import abdulmanov.eduard.timetable.presentation.login.sign_in.SignInFragment
import abdulmanov.eduard.timetable.presentation.login.sign_up.SignUpFragment
import abdulmanov.eduard.timetable.presentation.main.MainActivity
import abdulmanov.eduard.timetable.presentation.multipleclass.MultipleClassFragment
import abdulmanov.eduard.timetable.presentation.note.NoteFragment
import abdulmanov.eduard.timetable.presentation.onetimeclass.OneTimeClassFragment
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
        DomainModule::class
    ]
)
interface AppComponent{

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(loginActivity: LoginActivity)

    fun inject(signInFragment: SignInFragment)

    fun inject(signUpFragment: SignUpFragment)

    fun inject(mainActivity: MainActivity)

    fun inject(timetableFragment: TimetableFragment)

    fun inject(multipleClassFragment: MultipleClassFragment)

    fun inject(oneTimeClassFragment: OneTimeClassFragment)

    fun inject(noteFragment: NoteFragment)
}