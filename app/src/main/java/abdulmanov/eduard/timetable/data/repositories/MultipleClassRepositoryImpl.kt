package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.MultipleClassNetModel
import abdulmanov.eduard.timetable.domain.models.MultipleClass
import abdulmanov.eduard.timetable.domain.repositories.MultipleClassRepository
import io.reactivex.Completable

class MultipleClassRepositoryImpl(private val timetableApi: TimetableApi): MultipleClassRepository {

    override fun createMultipleClass(multipleClass: MultipleClass): Completable {
        val multipleClassNetModel = MultipleClassNetModel.fromDomain(multipleClass)

        return timetableApi.createMultipleClass(multipleClassNetModel)
            .ignoreElement()
    }

    override fun updateMultipleClass(multipleClassId: Int, multipleClass: MultipleClass): Completable {
        val multipleClassNetModel = MultipleClassNetModel.fromDomain(multipleClass)

        return timetableApi.updateMultipleClass(multipleClassId, multipleClassNetModel)
            .ignoreElement()
    }

    override fun deleteMultipleClass(multipleClassId: Int): Completable {
        return timetableApi.deleteMultipleClass(multipleClassId)
    }
}