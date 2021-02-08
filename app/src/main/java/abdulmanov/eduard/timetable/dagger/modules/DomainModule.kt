package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.domain.interactors.*
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.NoteRepository
import abdulmanov.eduard.timetable.domain.repositories.SettingRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideAuthInteractor(
        authRepository: AuthRepository,
        timetableRepository: TimetableRepository
    ): AuthInteractor {
        return AuthInteractor(authRepository, timetableRepository)
    }

    @Singleton
    @Provides
    fun provideTimetableInteractor(
        authRepository: AuthRepository,
        timetableRepository: TimetableRepository
    ): TimetableInteractor {
        return TimetableInteractor(authRepository, timetableRepository)
    }

    @Singleton
    @Provides
    fun provideLawInteractor(
        authRepository: AuthRepository,
        timetableRepository: TimetableRepository
    ): LawInteractor {
        return LawInteractor(authRepository, timetableRepository)
    }

    @Singleton
    @Provides
    fun provideSettingInteractor(
        authRepository: AuthRepository,
        timetableRepository: TimetableRepository,
        settingRepository: SettingRepository
    ): SettingInteractor {
        return SettingInteractor(authRepository, timetableRepository, settingRepository)
    }

    @Singleton
    @Provides
    fun provideNoteInteractor(repository: NoteRepository): NoteInteractor {
        return NoteInteractor(repository)
    }
}