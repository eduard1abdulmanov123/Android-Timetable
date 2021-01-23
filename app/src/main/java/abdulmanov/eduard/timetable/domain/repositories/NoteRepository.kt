package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.Note
import io.reactivex.Completable
import io.reactivex.Single

interface NoteRepository {

    fun createNote(note: Note): Completable

}