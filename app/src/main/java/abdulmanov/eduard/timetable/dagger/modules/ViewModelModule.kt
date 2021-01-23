package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.dagger.annotations.ViewModelKey
import abdulmanov.eduard.timetable.presentation._common.viewmodel.ViewModelFactory
import abdulmanov.eduard.timetable.presentation.login.sign_in.SignInViewModel
import abdulmanov.eduard.timetable.presentation.login.sign_up.SignUpViewModel
import abdulmanov.eduard.timetable.presentation.events.multipleclass.MultipleClassViewModel
import abdulmanov.eduard.timetable.presentation.events.note.NoteViewModel
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.OneTimeClassViewModel
import abdulmanov.eduard.timetable.presentation.onboarding.create.CreateTimetableViewModel
import abdulmanov.eduard.timetable.presentation.onboarding.join.JoinTimetableViewModel
import abdulmanov.eduard.timetable.presentation.timetable.TimetableViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateTimetableViewModel::class)
    abstract fun bindCreateTimetableViewModel(viewModel: CreateTimetableViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(JoinTimetableViewModel::class)
    abstract fun bindJoinTimetableViewModel(viewModel: JoinTimetableViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimetableViewModel::class)
    abstract fun bindTimetableViewModel(viewModel: TimetableViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MultipleClassViewModel::class)
    abstract fun bindMultipleClassViewModel(viewModel:MultipleClassViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OneTimeClassViewModel::class)
    abstract fun bindOneTimeClassViewModel(viewModel: OneTimeClassViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel::class)
    abstract fun bindNoteViewModel(viewModel: NoteViewModel): ViewModel
}