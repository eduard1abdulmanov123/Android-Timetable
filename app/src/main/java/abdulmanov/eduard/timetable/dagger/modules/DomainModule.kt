package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideAuthInteractor(authRepository: AuthRepository): AuthInteractor {
        return AuthInteractor(authRepository)
    }
}