package abdulmanov.eduard.timetable.data.local.database

import abdulmanov.eduard.timetable.data.local.database.dao.MultipleClassDao
import abdulmanov.eduard.timetable.data.local.database.dao.NoteDao
import abdulmanov.eduard.timetable.data.local.database.dao.OneTimeClassDao
import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel
import abdulmanov.eduard.timetable.data.local.database.models.NoteDbModel
import abdulmanov.eduard.timetable.data.local.database.models.OneTimeClassDbModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NoteDbModel::class, MultipleClassDbModel::class, OneTimeClassDbModel::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    abstract val multipleClassDao: MultipleClassDao

    abstract val oneTimeClassDao: OneTimeClassDao

    fun clearAll(){
        noteDao.deleteAll()
        multipleClassDao.deleteAll()
        oneTimeClassDao.deleteAll()
    }

    companion object {
        private const val DATABASE_NAME = "timetable_database"

        fun getRootDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}