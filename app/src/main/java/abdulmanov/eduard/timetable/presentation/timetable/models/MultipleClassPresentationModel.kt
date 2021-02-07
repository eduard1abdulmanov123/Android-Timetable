package abdulmanov.eduard.timetable.presentation.timetable.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MultipleClassPresentationModel(
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
): Parcelable