package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.local.sharedpreferences.TimetableSharedPreferences
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideAuthSharedPreferences(context: Context): AuthSharedPreferences{
        return AuthSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideTimetableSharedPreferences(context: Context): TimetableSharedPreferences {
        return TimetableSharedPreferences(context)
    }
}