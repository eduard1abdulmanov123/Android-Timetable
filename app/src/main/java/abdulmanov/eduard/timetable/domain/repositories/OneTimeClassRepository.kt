package abdulmanov.eduard.timetable.domain.repositories

import abdulmanov.eduard.timetable.domain.models.OneTimeClass
import io.reactivex.Completable

interface OneTimeClassRepository {

    fun createOneTimeClass(oneTimeClass: OneTimeClass): Completable

    fun updateOneTimeClass(oneTimeClassId:Int, oneTimeClass: OneTimeClass): Completable

    fun deleteOneTimeClass(oneTimeClassId: Int): Completable
}