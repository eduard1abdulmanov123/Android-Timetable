package abdulmanov.eduard.timetable.data.local.database.dao

import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel
import abdulmanov.eduard.timetable.data.local.database.models.NoteDbModel
import abdulmanov.eduard.timetable.data.local.database.models.OneTimeClassDbModel
import androidx.room.*
import io.reactivex.Observable

@Dao
abstract class NoteDao {

    @Transaction
    open fun updateNotes(notes: List<NoteDbModel>){
        deleteAll()
        insertNotes(notes)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNotes(notes: List<NoteDbModel>)

    @Query("DELETE FROM ${NoteDbModel.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("DELETE FROM ${NoteDbModel.TABLE_NAME} WHERE ${NoteDbModel.COLUMN_ID} = :id")
    abstract fun deleteById(id: Int)

    @Query("SELECT * FROM ${MultipleClassDbModel.TABLE_NAME}")
    abstract fun getNotes(): Observable<List<NoteDbModel>>
}