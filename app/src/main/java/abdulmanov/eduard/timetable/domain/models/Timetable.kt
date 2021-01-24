package abdulmanov.eduard.timetable.domain.models

data class Timetable(
    val id: Int = -1,
    val creatorUsername: String,
    val link: String,
    val typeWeek: TypeWeek,
    val dateUpdate: Long
)