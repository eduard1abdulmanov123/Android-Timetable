package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.Note
import io.reactivex.Completable

interface NoteRepository {

    fun createNote(note: Note): Completable
}