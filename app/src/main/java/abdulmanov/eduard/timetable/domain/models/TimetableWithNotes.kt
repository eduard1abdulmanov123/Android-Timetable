package abdulmanov.eduard.timetable.domain.models

data class TimetableWithNotes(
    val timetable: Timetable,
    val notes: List<Note>
)