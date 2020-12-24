package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.repositories.AuthRepositoryImpl
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
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
}