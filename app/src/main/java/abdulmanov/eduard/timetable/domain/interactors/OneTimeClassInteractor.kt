package abdulmanov.eduard.timetable.domain.interactors

import abdulmanov.eduard.timetable.domain.models.OneTimeClass
import abdulmanov.eduard.timetable.domain.repositories.OneTimeClassRepository
import io.reactivex.Completable

class OneTimeClassInteractor(private val oneTimeClassRepository: OneTimeClassRepository) {

    fun createOneTimeClass(oneTimeClass: OneTimeClass): Completable {
        return oneTimeClassRepository.createOneTimeClass(oneTimeClass)
    }

    fun updateOneTimeClass(oneTimeClassId:Int, oneTimeClass: OneTimeClass): Completable {
        return oneTimeClassRepository.updateOneTimeClass(oneTimeClassId, oneTimeClass)
    }

    fun deleteOneTimeClass(oneTimeClassId: Int): Completable {
        return oneTimeClassRepository.deleteOneTimeClass(oneTimeClassId)
    }
}