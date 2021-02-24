package abdulmanov.eduard.timetable.data.local.database.models

import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel.Companion.COLUMN_ID
import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel.Companion.TABLE_NAME
import abdulmanov.eduard.timetable.domain.models.MultipleClass
import abdulmanov.eduard.timetable.domain.models.Periodicity
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = TABLE_NAME, primaryKeys = [COLUMN_ID])
data class MultipleClassDbModel(
    @ColumnInfo(name =COLUMN_ID)
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
    @ColumnInfo(name = COLUMN_DAY_OF_WEEK)
    val dayOfWeek: Int,
    @ColumnInfo(name = COLUMN_PERIODICITY)
    val periodicity: Int,
    @ColumnInfo(name = COLUMN_CANCELED_CLASSES)
    val canceledClasses: String,
) {
    companion object {
        const val TABLE_NAME = "multiple_class"

        const val COLUMN_ID = "multiple_class_id"
        const val COLUMN_NAME_SUBJECT = "name_subject"
        const val COLUMN_NAME_TEACHER = "name_teacher"
        const val COLUMN_AUDIENCE = "audience"
        const val COLUMN_TYPE_CLASS = "type_class"
        const val COLUMN_START_OF_CLASS = "start_of_class"
        const val COLUMN_END_OF_CLASS = "end_of_class"
        const val COLUMN_DAY_OF_WEEK = "day_of_week"
        const val COLUMN_PERIODICITY = "periodicity"
        const val COLUMN_CANCELED_CLASSES = "canceled_classes"

        fun toDomain(multipleClasses: List<MultipleClassDbModel>): List<MultipleClass> {
            return multipleClasses.map(::toDomain)
        }

        fun toDomain(multipleClass: MultipleClassDbModel): MultipleClass {
            return MultipleClass(
                id = multipleClass.id,
                nameSubject = multipleClass.nameSubject,
                nameTeacher = multipleClass.nameTeacher,
                audience = multipleClass.audience,
                typeClass = multipleClass.typeClass,
                startOfClass = multipleClass.startOfClass,
                endOfClass = multipleClass.endOfClass,
                dayOfWeek = multipleClass.dayOfWeek,
                periodicity = multipleClass.periodicity,
                canceledClasses = multipleClass.canceledClasses
            )
        }
    }
}