package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.NoteNetModel
import abdulmanov.eduard.timetable.domain.models.Note
import abdulmanov.eduard.timetable.domain.repositories.NotesRepository
import io.reactivex.Completable
import io.reactivex.Single

class NotesRepositoryImpl(private val timetableApi: TimetableApi): NotesRepository {

    override fun getNotes(refresh: Boolean): Single<List<Note>> {
        return Single.zip(
            timetableApi.getNotesForTimetableOwner(),
            timetableApi.getNotes(),
            { notesForOwner, notes ->
                notesForOwner.plus(notes).distinctBy { it.id }
            }
        ).map { notes-> notes.map { NoteNetModel.toDomain(it) } }
    }

    override fun createNote(note: Note): Completable {
        val noteNetModel = NoteNetModel.fromDomain(note)

        return timetableApi.createNote(noteNetModel)
            .ignoreElement()
    }

    override fun updateNote(noteId: Int, note: Note): Completable {
        val noteNetModel = NoteNetModel.fromDomain(note)

        return timetableApi.updateNote(noteId,noteNetModel)
            .ignoreElement()
    }

    override fun deleteNote(noteId: Int): Completable {
        return timetableApi.deleteNote(noteId)
    }
}