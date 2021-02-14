package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.NoteNetModel
import abdulmanov.eduard.timetable.domain.models.Note
import abdulmanov.eduard.timetable.domain.repositories.NoteRepository
import io.reactivex.Completable

class NoteRepositoryImpl(private val timetableApi: TimetableApi): NoteRepository {

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