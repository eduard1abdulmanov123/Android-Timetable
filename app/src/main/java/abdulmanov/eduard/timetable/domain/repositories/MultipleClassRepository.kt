package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.MultipleClass
import io.reactivex.Completable

interface MultipleClassRepository {

    fun createMultipleClass(multipleClass: MultipleClass): Completable

    fun updateMultipleClass(multipleClassId: Int, multipleClass: MultipleClass): Completable

    fun deleteMultipleClass(multipleClassId: Int): Completable
}