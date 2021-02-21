package abdulmanov.eduard.timetable.presentation.timetable

sealed class TimetableState {
    object Empty: TimetableState()
    data class Data(val items: List<Any>) : TimetableState()
}