package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.Note
import abdulmanov.eduard.timetable.domain.repositories.NotesRepository
import io.reactivex.Completable

class NoteInteractor(private val noteRepository: NotesRepository) {

    fun createNote(note: Note): Completable {
        return noteRepository.createNote(note)
    }

    fun updateNote(noteId: Int, note: Note): Completable {
        return noteRepository.updateNote(noteId, note)
    }

    fun deleteNote(noteId: Int): Completable {
        return noteRepository.deleteNote(noteId)
    }
}