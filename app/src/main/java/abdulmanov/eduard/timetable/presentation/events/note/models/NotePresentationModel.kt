package abdulmanov.eduard.timetable.presentation.events.note.models

import abdulmanov.eduard.timetable.domain.models.Note
import abdulmanov.eduard.timetable.presentation.events.common.ItemToBeSortedByTime
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class NotePresentationModel(
    val id: Int = -1,
    var content: String = "",
    var date: String = "",
    var time: String = "",
    var visibility: Boolean = false,
    val username: String = ""
): Parcelable, ItemToBeSortedByTime {

    override val timeToSort get() = time

    fun isNew(): Boolean = id == -1

    companion object {

        private val DATE_FORMATTER_PRESENTER = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")

        fun fromDomain(notes: List<Note>): List<NotePresentationModel> {
            return notes.map(::fromDomain)
        }

        fun fromDomain(note: Note): NotePresentationModel {
            return NotePresentationModel(
                id = note.id,
                content = note.content,
                date = DATE_FORMATTER_PRESENTER.format(LocalDate.parse(note.date)),
                time = note.time,
                visibility = note.visibility,
                username = note.username
            )
        }

        fun toDomain(note: NotePresentationModel): Note {
            return Note(
                id = note.id,
                content = note.content,
                date = LocalDate.parse(note.date, DATE_FORMATTER_PRESENTER).toString(),
                time = note.time,
                visibility = note.visibility
            )
        }
    }
}