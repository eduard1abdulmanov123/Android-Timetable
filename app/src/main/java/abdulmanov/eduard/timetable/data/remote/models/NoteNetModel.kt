package abdulmanov.eduard.timetable.data.remote.models

import abdulmanov.eduard.timetable.data.local.database.models.NoteDbModel
import abdulmanov.eduard.timetable.domain.models.Note
import com.google.gson.annotations.SerializedName

class NoteNetModel {

    data class Response(
        @SerializedName("id")
        val id: Int,
        @SerializedName("content")
        val content: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("time")
        val time: String,
        @SerializedName("visibility")
        val visibility: Boolean
    )

    data class Request(
        @SerializedName("content")
        val content: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("time")
        val time: String,
        @SerializedName("visibility")
        val visibility: Boolean
    )

    companion object {

        fun toDatabase(note: Response): NoteDbModel {
            return NoteDbModel(
                id = note.id,
                content = note.content,
                date = note.date,
                time = note.time,
                visibility = note.visibility
            )
        }

        fun toDomain(note: Response): Note {
            return Note(
                id = note.id,
                content = note.content,
                date = note.date,
                time = note.time,
                visibility = note.visibility
            )
        }

        fun fromDomain(note: Note): Request {
            return Request(
                content = note.content,
                date = note.date,
                time = note.time,
                visibility = note.visibility
            )
        }
    }
}