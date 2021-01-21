package abdulmanov.eduard.timetable.presentation.events.note.mappers

import abdulmanov.eduard.timetable.domain.models.Note
import javax.inject.Inject

class NoteMapperPresentation @Inject constructor() {

    fun dataToNote(content: String, date: String, time: String, visibility: Boolean) = Note(
        content = content,
        date = date,
        time = time,
        visibility = visibility
    )
}