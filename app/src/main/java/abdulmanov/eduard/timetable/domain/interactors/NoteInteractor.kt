package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.Note
import abdulmanov.eduard.timetable.domain.repositories.NoteRepository
import io.reactivex.Completable

class NoteInteractor(private val noteRepository: NoteRepository) {

    fun createNote(note: Note): Completable {
        return noteRepository.createNote(note)
    }
}