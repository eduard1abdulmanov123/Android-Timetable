package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.NoteNetModel
import abdulmanov.eduard.timetable.domain.models.Note
import abdulmanov.eduard.timetable.domain.repositories.NoteRepository
import android.util.Log
import io.reactivex.Completable

class NoteRepositoryImpl(
    private val timetableApi: TimetableApi
): NoteRepository {

    override fun createNote(note: Note): Completable {
        val noteNetModel = NoteNetModel.Request(
            content = note.content,
            date = note.date,
            time = note.time,
            visibility = note.visibility
        )

        return timetableApi.createNote(noteNetModel)
            .doOnSuccess {
                Log.d("FuckFuck", it.toString())
            }
            .ignoreElement()
    }

}