package abdulmanov.eduard.timetable.domain.models

data class OneTimeClass(
    val id: Int = -1,
    val nameSubject: String,
    val nameTeacher: String,
    val audience: String,
    val typeClass: String,
    val startOfClass: String,
    val endOfClass: String,
    val dateOfClass: String
)