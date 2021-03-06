package abdulmanov.eduard.timetable.data.local.database.dao

import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel
import androidx.room.*
import io.reactivex.Single

@Dao
abstract class MultipleClassDao {

    @Transaction
    open fun updateMultipleClasses(multipleClasses: List<MultipleClassDbModel>){
        deleteAll()
        insertMultipleClasses(multipleClasses)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMultipleClasses(multipleClasses: List<MultipleClassDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMultipleClass(multipleClass: MultipleClassDbModel)

    @Query("DELETE FROM ${MultipleClassDbModel.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("DELETE FROM ${MultipleClassDbModel.TABLE_NAME} WHERE ${MultipleClassDbModel.COLUMN_ID} = :id")
    abstract fun deleteById(id: Int)

    @Query("SELECT * FROM ${MultipleClassDbModel.TABLE_NAME}")
    abstract fun getMultipleClasses(): Single<List<MultipleClassDbModel>>
}