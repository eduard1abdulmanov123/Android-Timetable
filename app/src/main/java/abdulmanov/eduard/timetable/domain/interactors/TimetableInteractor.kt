package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.*
import abdulmanov.eduard.timetable.domain.repositories.AuthRepository
import abdulmanov.eduard.timetable.domain.repositories.NotesRepository
import abdulmanov.eduard.timetable.domain.repositories.TimetableRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

class TimetableInteractor(
    private val authRepository: AuthRepository,
    private val timetableRepository: TimetableRepository,
    private val notesRepository: NotesRepository
) {

    fun createTimetable(typeWeek: TypeWeek): Completable{
        return timetableRepository.createTimetable(typeWeek)
            .doOnSuccess {
                timetableRepository.saveTimetableInfo(it)
                attachUserToTimetable(it)
            }
            .ignoreElement()
    }

    fun joinTimetable(link: String): Completable {
        return timetableRepository.joinTimetable(link)
            .doOnSuccess {
                timetableRepository.saveTimetableInfo(it)
                attachUserToTimetable(it)
            }
            .ignoreElement()
    }

    private fun attachUserToTimetable(timetable: Timetable){
        val updatedUser = authRepository.getUser().copy(currentTimetableId = timetable.id)
        authRepository.saveUser(updatedUser)
    }

    fun fetchTimetableAndNote(): Completable {
        return timetableRepository.fetchTimetable()
            .andThen(notesRepository.fetchNotes())
    }

    fun getTimetableForSelectedDate(date: LocalDate):Single<TimetableWithNotes> {
        return Single.zip(
            timetableRepository.getTimetable(),
            notesRepository.getNotes(),
            { timetable, notes ->
                TimetableWithNotes(
                    timetable =  timetable.copy(
                        multipleClasses = getMultipleClassesForSelectedDate(timetable, date),
                        oneTimeClasses = getOneTimeClassesForSelectedDate(timetable, date)
                    ),
                    notes =getNotesForSelectedDate(notes, date)
                )
            }
        )
    }

    fun getTypeWeekForDate(date: LocalDate): TypeWeek {
        return timetableRepository.getTypeWeekForDate(date)
    }

    private fun getMultipleClassesForSelectedDate(timetable: Timetable, date: LocalDate): List<MultipleClass> {
        return timetable.multipleClasses
            .filter {
                val isPeriodicity = (it.periodicity == 0 || it.periodicity == timetableRepository.getTypeWeekForDate(date).number)
                val isDayOfWeek = (it.dayOfWeek == date.dayOfWeek.value)
                isPeriodicity && isDayOfWeek
            }
    }

    private fun getOneTimeClassesForSelectedDate(timetable: Timetable, date: LocalDate): List<OneTimeClass> {
        return timetable.oneTimeClasses
            .filter { it.dateOfClass == date.toString() }
    }

    private fun getNotesForSelectedDate(notes: List<Note>, date: LocalDate): List<Note> {
        return notes.filter { it.date == date.toString() }
    }
}