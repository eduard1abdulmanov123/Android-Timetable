package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.local.database.AppDatabase
import abdulmanov.eduard.timetable.data.local.database.dao.MultipleClassDao
import abdulmanov.eduard.timetable.data.local.database.dao.NoteDao
import abdulmanov.eduard.timetable.data.local.database.dao.OneTimeClassDao
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getRootDatabase(context)
    }

    @Singleton
    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao
    }

    @Singleton
    @Provides
    fun provideOneTimeClassDao(appDatabase: AppDatabase): OneTimeClassDao {
        return appDatabase.oneTimeClassDao
    }

    @Singleton
    @Provides
    fun provideMultipleClassDao(appDatabase: AppDatabase): MultipleClassDao {
        return appDatabase.multipleClassDao
    }
}