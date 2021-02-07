package abdulmanov.eduard.timetable.domain.models

data class MultipleClass(
    val id: Int,
    val nameSubject: String,
    val nameTeacher: String,
    val audience: String,
    val typeClass: String,
    val color: String,
    val startOfClass: String,
    val endOfClass: String,
    val dayOfWeek: Int,
    val periodicity: Int
)