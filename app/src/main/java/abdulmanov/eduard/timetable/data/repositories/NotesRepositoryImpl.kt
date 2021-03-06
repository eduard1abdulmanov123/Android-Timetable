package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.database.dao.NoteDao
import abdulmanov.eduard.timetable.data.local.database.models.NoteDbModel
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.NoteNetModel
import abdulmanov.eduard.timetable.domain.models.Note
import abdulmanov.eduard.timetable.domain.repositories.NotesRepository
import io.reactivex.Completable
import io.reactivex.Single

class NotesRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val noteDao: NoteDao
): NotesRepository {

    override fun fetchNotes(): Completable {
        return Single.zip(
            timetableApi.getNotesForTimetableOwner(),
            timetableApi.getNotes(),
            { notesForOwner, notes -> notesForOwner.plus(notes).distinctBy { it.id } }
        )
            .map(NoteNetModel::toDatabase)
            .doOnSuccess(noteDao::updateNotes)
            .ignoreElement()
    }

    override fun getNotes(): Single<List<Note>> {
        return noteDao.getNotes()
            .map(NoteDbModel::toDomain)
    }

    override fun createNote(note: Note): Completable {
        val noteNetModel = NoteNetModel.fromDomain(note)

        return timetableApi.createNote(noteNetModel)
            .map(NoteNetModel::toDatabase)
            .doOnSuccess(noteDao::insertNote)
            .ignoreElement()
    }

    override fun updateNote(noteId: Int, note: Note): Completable {
        val noteNetModel = NoteNetModel.fromDomain(note)

        return timetableApi.updateNote(noteId, noteNetModel)
            .map(NoteNetModel::toDatabase)
            .doOnSuccess(noteDao::insertNote)
            .ignoreElement()
    }

    override fun deleteNote(noteId: Int): Completable {
        return timetableApi.deleteNote(noteId)
            .doOnComplete { noteDao.deleteById(noteId) }
    }
}