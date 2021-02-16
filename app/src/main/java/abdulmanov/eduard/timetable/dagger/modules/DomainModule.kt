package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.domain.interactors.*
import abdulmanov.eduard.timetable.domain.repositories.*
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
        timetableRepository: TimetableRepository,
        notesRepository: NotesRepository
    ): TimetableInteractor {
        return TimetableInteractor(authRepository, timetableRepository, notesRepository)
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
    fun provideMultipleClassInteractor(repository: MultipleClassRepository): MultipleClassInteractor {
        return MultipleClassInteractor(repository)
    }

    @Singleton
    @Provides
    fun provideOneTimeClassInteractor(repository: OneTimeClassRepository): OneTimeClassInteractor {
        return OneTimeClassInteractor(repository)
    }

    @Singleton
    @Provides
    fun provideNoteInteractor(repository: NotesRepository): NoteInteractor {
        return NoteInteractor(repository)
    }
}