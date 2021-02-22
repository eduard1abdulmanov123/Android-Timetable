package abdulmanov.eduard.timetable.data.local.database.models

import abdulmanov.eduard.timetable.data.local.database.models.NoteDbModel.Companion.COLUMN_ID
import abdulmanov.eduard.timetable.data.local.database.models.NoteDbModel.Companion.TABLE_NAME
import abdulmanov.eduard.timetable.domain.models.Note
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = TABLE_NAME, primaryKeys = [COLUMN_ID])
data class NoteDbModel(
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = COLUMN_CONTENT)
    val content: String,
    @ColumnInfo(name = COLUMN_DATE)
    val date: String,
    @ColumnInfo(name = COLUMN_TIME)
    val time: String,
    @ColumnInfo(name = COLUMN_VISIBILITY)
    val visibility: Boolean,
    @ColumnInfo(name = COLUMN_USER_NAME)
    val userName: String
) {
    companion object {
        const val TABLE_NAME = "note"

        const val COLUMN_ID = "note_id"
        const val COLUMN_CONTENT = "note_content"
        const val COLUMN_DATE = "note_date"
        const val COLUMN_TIME = "note_tme"
        const val COLUMN_VISIBILITY = "note_visibility"
        const val COLUMN_USER_NAME = "user_name"

        fun toDomain(notes: List<NoteDbModel>): List<Note> {
            return notes.map(::toDomain)
        }

        fun toDomain(note: NoteDbModel): Note {
            return Note(
                id = note.id,
                content = note.content,
                date = note.date,
                time = note.time,
                visibility = note.visibility,
                username = note.userName
            )
        }
    }
}