package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.mappers.TimetableMapperDomain
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.repositories.AuthRepositoryImpl
import abdulmanov.eduard.timetable.data.repositories.NoteRepositoryImpl
import abdulmanov.eduard.timetable.data.repositories.TimetableRepositoryImpl
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.NoteRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideTimetableMapperDomain(): TimetableMapperDomain {
        return TimetableMapperDomain()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        timetableApi: TimetableApi,
        authSharedPreferences: AuthSharedPreferences
    ): AuthRepository{
        return AuthRepositoryImpl(timetableApi, authSharedPreferences)
    }

    @Singleton
    @Provides
    fun provideTimetableRepository(
        timetableApi: TimetableApi,
        timetableMapperDomain: TimetableMapperDomain,
        authSharedPreferences: AuthSharedPreferences,
        timetableSharedPreferences: TimetableSharedPreferences
    ): TimetableRepository {
        return TimetableRepositoryImpl(timetableApi, timetableMapperDomain, authSharedPreferences, timetableSharedPreferences)
    }

    @Singleton
    @Provides
    fun provideNoteRepository(
        timetableApi: TimetableApi
    ): NoteRepository {
        return NoteRepositoryImpl(timetableApi)
    }
}