package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.repositories.*
import abdulmanov.eduard.timetable.domain.repositories.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

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
        timetableSharedPreferences: TimetableSharedPreferences
    ): TimetableRepository {
        return TimetableRepositoryImpl(timetableApi, timetableSharedPreferences)
    }

    @Singleton
    @Provides
    fun provideSettingRepository(timetableApi: TimetableApi): SettingRepository {
        return SettingRepositoryImpl(timetableApi)
    }

    @Singleton
    @Provides
    fun provideMultipleClassRepository(timetableApi: TimetableApi): MultipleClassRepository {
        return MultipleClassRepositoryImpl(timetableApi)
    }

    @Singleton
    @Provides
    fun provideOneTimeClassRepository(timetableApi: TimetableApi): OneTimeClassRepository {
        return OneTimeClassRepositoryImpl(timetableApi)
    }

    @Singleton
    @Provides
    fun provideNoteRepository(timetableApi: TimetableApi): NotesRepository {
        return NotesRepositoryImpl(timetableApi)
    }
}