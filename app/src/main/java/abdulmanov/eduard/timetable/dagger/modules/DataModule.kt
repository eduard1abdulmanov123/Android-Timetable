package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.local.database.dao.MultipleClassDao
import abdulmanov.eduard.timetable.data.local.database.dao.NoteDao
import abdulmanov.eduard.timetable.data.local.database.dao.OneTimeClassDao
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
        multipleClassDao: MultipleClassDao,
        oneTimeClassDao: OneTimeClassDao,
        timetableSharedPreferences: TimetableSharedPreferences
    ): TimetableRepository {
        return TimetableRepositoryImpl(
            timetableApi,
            multipleClassDao,
            oneTimeClassDao,
            timetableSharedPreferences
        )
    }

    @Singleton
    @Provides
    fun provideSettingRepository(timetableApi: TimetableApi): SettingRepository {
        return SettingRepositoryImpl(timetableApi)
    }

    @Singleton
    @Provides
    fun provideMultipleClassRepository(timetableApi: TimetableApi, multipleClassDao: MultipleClassDao): MultipleClassRepository {
        return MultipleClassRepositoryImpl(timetableApi, multipleClassDao)
    }

    @Singleton
    @Provides
    fun provideOneTimeClassRepository(timetableApi: TimetableApi, oneTimeClassDao: OneTimeClassDao): OneTimeClassRepository {
        return OneTimeClassRepositoryImpl(timetableApi, oneTimeClassDao)
    }

    @Singleton
    @Provides
    fun provideNoteRepository(timetableApi: TimetableApi, noteDao: NoteDao): NotesRepository {
        return NotesRepositoryImpl(timetableApi, noteDao)
    }
}