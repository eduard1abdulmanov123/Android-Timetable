package abdulmanov.eduard.timetable.presentation.timetable

sealed class TimetableState {
    object Progress: TimetableState()
    object Empty: TimetableState()
    object Error: TimetableState()
    data class Data(val items: List<Any>) : TimetableState()
    data class Refresh(val lastState: TimetableState): TimetableState()
}