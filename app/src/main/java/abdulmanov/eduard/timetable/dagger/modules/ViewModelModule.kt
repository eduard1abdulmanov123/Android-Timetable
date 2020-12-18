package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.dagger.annotations.ViewModelKey
import abdulmanov.eduard.timetable.presentation._common.viewmodel.ViewModelFactory
import abdulmanov.eduard.timetable.presentation.login.sign_in.SignInViewModel
import abdulmanov.eduard.timetable.presentation.login.sign_up.SignUpViewModel
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
}