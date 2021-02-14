package abdulmanov.eduard.timetable.presentation.events.note.models

import abdulmanov.eduard.timetable.domain.models.Note
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotePresentationModel(
    val id: Int = -1,
    var content: String = "",
    var date: String = "",
    var time: String = "",
    var visibility: Boolean = false
): Parcelable {

    fun isNew(): Boolean = id == -1

    companion object {

        fun fromDomain(note: Note): NotePresentationModel {
            return NotePresentationModel(
                id = note.id,
                content = note.content,
                date = note.date,
                time = note.time,
                visibility = note.visibility
            )
        }

        fun toDomain(note: NotePresentationModel): Note {
            return Note(
                id = note.id,
                content = note.content,
                date = note.date,
                time = note.time,
                visibility = note.visibility
            )
        }
    }
}