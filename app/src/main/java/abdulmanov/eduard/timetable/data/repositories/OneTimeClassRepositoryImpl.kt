package abdulmanov.eduard.timetable.data.repositories

import abdulmanov.eduard.timetable.data.local.database.dao.OneTimeClassDao
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import abdulmanov.eduard.timetable.data.remote.models.OneTimeClassNetModel
import abdulmanov.eduard.timetable.domain.models.OneTimeClass
import abdulmanov.eduard.timetable.domain.repositories.OneTimeClassRepository
import io.reactivex.Completable

class OneTimeClassRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val oneTimeClassDao: OneTimeClassDao
): OneTimeClassRepository {

    override fun createOneTimeClass(oneTimeClass: OneTimeClass): Completable {
        val oneTimeClassNetModel = OneTimeClassNetModel.fromDomain(oneTimeClass)

        return timetableApi.createOneTimeClass(oneTimeClassNetModel)
            .map(OneTimeClassNetModel::toDatabase)
            .doOnSuccess(oneTimeClassDao::insertOneTimeClass)
            .ignoreElement()
    }

    override fun updateOneTimeClass(oneTimeClassId: Int, oneTimeClass: OneTimeClass): Completable {
        val oneTimeClassNetModel = OneTimeClassNetModel.fromDomain(oneTimeClass)

        return timetableApi.updateOneTimeClass(oneTimeClassId, oneTimeClassNetModel)
            .map(OneTimeClassNetModel::toDatabase)
            .doOnSuccess(oneTimeClassDao::insertOneTimeClass)
            .ignoreElement()
    }

    override fun deleteOneTimeClass(oneTimeClassId: Int): Completable {
        return timetableApi.deleteOneTimeClass(oneTimeClassId)
            .doOnComplete { oneTimeClassDao.deleteById(oneTimeClassId) }
    }
}