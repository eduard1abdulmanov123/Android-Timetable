package abdulmanov.eduard.timetable.domain.models

data class Note(
    val id: Int = -1,
    val content: String,
    val date: String,
    val time: String,
    val visibility: Boolean
)