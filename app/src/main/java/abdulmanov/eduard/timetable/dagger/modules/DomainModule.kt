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
        timetableInteractor: TimetableInteractor
    ): AuthInteractor {
        return AuthInteractor(authRepository, timetableInteractor)
    }

    @Singleton
    @Provides
    fun provideTimetableInteractor(
        timetableRepository: TimetableRepository,
        notesRepository: NotesRepository
    ): TimetableInteractor {
        return TimetableInteractor(timetableRepository, notesRepository)
    }

    @Singleton
    @Provides
    fun provideSettingInteractor(settingRepository: SettingRepository): SettingInteractor {
        return SettingInteractor(settingRepository)
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