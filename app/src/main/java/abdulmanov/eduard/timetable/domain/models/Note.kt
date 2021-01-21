package abdulmanov.eduard.timetable.domain.models

data class Note(
    val id: Int = 0,
    val content: String = "",
    val date: String = "",
    val time: String = "",
    val visibility: Boolean = false
)