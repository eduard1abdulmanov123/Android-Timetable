package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.domain.interactors.NoteInteractor
import abdulmanov.eduard.timetable.domain.interactors.TimetableInteractor
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.NoteRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideAuthInteractor(repository: AuthRepository): AuthInteractor {
        return AuthInteractor(repository)
    }

    @Singleton
    @Provides
    fun provideTimetableInteractor(repository: TimetableRepository): TimetableInteractor {
        return TimetableInteractor(repository)
    }

    @Singleton
    @Provides
    fun provideNoteInteractor(repository: NoteRepository): NoteInteractor {
        return NoteInteractor(repository)
    }
}