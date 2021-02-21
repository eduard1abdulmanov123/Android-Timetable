package abdulmanov.eduard.timetable.data.local.database.dao

import abdulmanov.eduard.timetable.data.local.database.models.OneTimeClassDbModel
import androidx.room.*
import io.reactivex.Single

@Dao
abstract class OneTimeClassDao {

    @Transaction
    open fun updateOneTimeClasses(oneTimeClasses: List<OneTimeClassDbModel>){
        deleteAll()
        insertOneTimeClasses(oneTimeClasses)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOneTimeClasses(oneTimeClasses: List<OneTimeClassDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOneTimeClass(oneTimeClass: OneTimeClassDbModel)

    @Query("DELETE FROM ${OneTimeClassDbModel.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("DELETE FROM ${OneTimeClassDbModel.TABLE_NAME} WHERE ${OneTimeClassDbModel.COLUMN_ID} = :id")
    abstract fun deleteById(id: Int)

    @Query("SELECT * FROM ${OneTimeClassDbModel.TABLE_NAME}")
    abstract fun getOneTimeClasses(): Single<List<OneTimeClassDbModel>>
}