package abdulmanov.eduard.timetable.domain.models

data class Timetable(
    val id: Int = -1,
    val creatorUsername: String,
    val link: String,
    val dateUpdate: String,
    val typeWeek: TypeWeek,
    val timeZone: String,
    val multipleClasses: List<MultipleClass> = listOf(),
    val oneTimeClasses: List<OneTimeClass> = listOf()
)