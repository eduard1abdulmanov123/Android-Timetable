package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.Note
import io.reactivex.Completable
import io.reactivex.Single

interface NotesRepository {

    fun getNotes(refresh: Boolean): Single<List<Note>>

    fun createNote(note: Note): Completable

    fun updateNote(noteId: Int, note: Note): Completable

    fun deleteNote(noteId: Int): Completable
}