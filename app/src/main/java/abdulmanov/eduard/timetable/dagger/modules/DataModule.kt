package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import abdulmanov.eduard.timetable.data.mappers.TimetableMapperDomain
import abdulmanov.eduard.timetable.data.mappers.UserMapperDomain
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
        userMapperDomain: UserMapperDomain,
        authSharedPreferences: AuthSharedPreferences
    ): AuthRepository{
        return AuthRepositoryImpl(timetableApi, userMapperDomain, authSharedPreferences)
    }

    @Singleton
    @Provides
    fun provideTimetableRepository(
        timetableApi: TimetableApi,
        timetableMapperDomain: TimetableMapperDomain,
        timetableSharedPreferences: TimetableSharedPreferences
    ): TimetableRepository {
        return TimetableRepositoryImpl(timetableApi, timetableMapperDomain, timetableSharedPreferences)
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
    fun provideNoteRepository(timetableApi: TimetableApi): NoteRepository {
        return NoteRepositoryImpl(timetableApi)
    }
}