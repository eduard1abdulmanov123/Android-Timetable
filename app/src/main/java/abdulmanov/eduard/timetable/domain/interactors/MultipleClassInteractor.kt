package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.MultipleClass
import abdulmanov.eduard.timetable.domain.repositories.MultipleClassRepository
import io.reactivex.Completable
import io.reactivex.Single

class MultipleClassInteractor(private val multipleClassRepository: MultipleClassRepository) {

    fun createMultipleClass(multipleClass: MultipleClass): Completable {
        return multipleClassRepository.createMultipleClass(multipleClass)
    }

    fun updateMultipleClass(multipleClassId: Int, multipleClass: MultipleClass): Completable {
        return multipleClassRepository.updateMultipleClass(multipleClassId, multipleClass)
    }

    fun deleteMultipleClass(multipleClassId: Int): Completable {
        return multipleClassRepository.deleteMultipleClass(multipleClassId)
    }
}