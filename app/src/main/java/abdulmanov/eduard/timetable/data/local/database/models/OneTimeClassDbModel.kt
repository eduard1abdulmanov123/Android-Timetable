package abdulmanov.eduard.timetable.data.local.database.models

import abdulmanov.eduard.timetable.data.local.database.models.OneTimeClassDbModel.Companion.COLUMN_ID
import abdulmanov.eduard.timetable.data.local.database.models.OneTimeClassDbModel.Companion.TABLE_NAME
import abdulmanov.eduard.timetable.domain.models.OneTimeClass
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = TABLE_NAME, primaryKeys = [COLUMN_ID])
data class OneTimeClassDbModel(
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = COLUMN_NAME_SUBJECT)
    val nameSubject: String,
    @ColumnInfo(name = COLUMN_NAME_TEACHER)
    val nameTeacher: String,
    @ColumnInfo(name = COLUMN_AUDIENCE)
    val audience: String,
    @ColumnInfo(name = COLUMN_TYPE_CLASS)
    val typeClass: String,
    @ColumnInfo(name = COLUMN_START_OF_CLASS)
    val startOfClass: String,
    @ColumnInfo(name = COLUMN_END_OF_CLASS)
    val endOfClass: String,
    @ColumnInfo(name = COLUMN_DATE_OF_CLASS)
    val dateOfClass: String
) {
    companion object {
        const val TABLE_NAME = "one_time_class"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME_SUBJECT = "name_subject"
        const val COLUMN_NAME_TEACHER = "name_teacher"
        const val COLUMN_AUDIENCE = "audience"
        const val COLUMN_TYPE_CLASS = "type_class"
        const val COLUMN_START_OF_CLASS = "start_of_class"
        const val COLUMN_END_OF_CLASS = "end_of_class"
        const val COLUMN_DATE_OF_CLASS = "date_of_class"

        fun toDomain(oneTimeClasses: List<OneTimeClassDbModel>): List<OneTimeClass> {
            return oneTimeClasses.map(::toDomain)
        }

        fun toDomain(oneTimeClass: OneTimeClassDbModel): OneTimeClass {
            return OneTimeClass(
                id = oneTimeClass.id,
                nameSubject = oneTimeClass.nameSubject,
                nameTeacher = oneTimeClass.nameTeacher,
                audience = oneTimeClass.audience,
                typeClass = oneTimeClass.typeClass,
                startOfClass = oneTimeClass.startOfClass,
                endOfClass = oneTimeClass.endOfClass,
                dateOfClass = oneTimeClass.dateOfClass
            )
        }
    }
}