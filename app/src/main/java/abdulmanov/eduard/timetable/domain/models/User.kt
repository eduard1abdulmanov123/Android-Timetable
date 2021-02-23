package abdulmanov.eduard.timetable.domain.models

data class User(
    val userName: String?,
    val token: String?,
    val refreshToken: String?,
    val currentTimetableId: Int?
)