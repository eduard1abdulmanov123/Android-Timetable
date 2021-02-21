package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.database.dao.MultipleClassDao
import abdulmanov.eduard.timetable.data.local.database.models.MultipleClassDbModel
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.MultipleClassNetModel
import abdulmanov.eduard.timetable.domain.models.MultipleClass
import abdulmanov.eduard.timetable.domain.repositories.MultipleClassRepository
import io.reactivex.Completable
import io.reactivex.Single

class MultipleClassRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val multipleClassDao: MultipleClassDao,
): MultipleClassRepository {

    override fun createMultipleClass(multipleClass: MultipleClass): Completable {
        val multipleClassNetModel = MultipleClassNetModel.fromDomain(multipleClass)

        return timetableApi.createMultipleClass(multipleClassNetModel)
            .map(MultipleClassNetModel::toDatabase)
            .doOnSuccess(multipleClassDao::insertMultipleClass)
            .ignoreElement()
    }

    override fun updateMultipleClass(multipleClassId: Int, multipleClass: MultipleClass): Completable {
        val multipleClassNetModel = MultipleClassNetModel.fromDomain(multipleClass)

        return timetableApi.updateMultipleClass(multipleClassId, multipleClassNetModel)
            .map(MultipleClassNetModel::toDatabase)
            .doOnSuccess(multipleClassDao::insertMultipleClass)
            .ignoreElement()
    }

    override fun deleteMultipleClass(multipleClassId: Int): Completable {
        return timetableApi.deleteMultipleClass(multipleClassId)
            .doOnComplete { multipleClassDao.deleteById(multipleClassId) }
    }
}