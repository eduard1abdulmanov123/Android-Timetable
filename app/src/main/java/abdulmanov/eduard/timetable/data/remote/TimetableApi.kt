package abdulmanov.eduard.timetable.data.remote

import abdulmanov.eduard.timetable.data.remote.models.MultipleClassNetModel
import abdulmanov.eduard.timetable.data.remote.models.NoteNetModel
import abdulmanov.eduard.timetable.data.remote.models.TimetableNetModel
import abdulmanov.eduard.timetable.data.remote.models.UserNetModel
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface TimetableApi {

    @POST("api/v1/auth/sing-up")
    fun signUp(@Body user: UserNetModel.Request): Single<UserNetModel.Response>

    @POST("api/v1/auth/sing-in")
    fun signIn(@Body user: UserNetModel.Request): Single<UserNetModel.Response>

    @POST("/api/v1/timetable/create")
    fun createTimetable(@Body timetable: TimetableNetModel.Request): Single<TimetableNetModel.Response>

    @POST
    fun joinTimetable(@Url url: String): Single<TimetableNetModel.Response>

    @GET("/api/v1/timetable/")
    fun getTimetable(): Single<TimetableNetModel.Response>

    @POST("/api/v1/timetable/changeTypeWeek")
    fun changeTypeWeekTheTimetable(@Body timetable: TimetableNetModel.Request): Completable

    /*MultipleClass*/

    @GET("/api/v1/multiple_class/{id}")
    fun getMultipleClass(@Path("id") multipleClassId: Int): Single<MultipleClassNetModel.Response>

    @POST("/api/v1/multiple_class/create")
    fun createMultipleClass(@Body multipleClass: MultipleClassNetModel.Request): Single<MultipleClassNetModel.Response>

    @POST("/api/v1/multiple_class/update/{id}")
    fun updateMultipleClass(
        @Path("id") multipleClassId: Int,
        @Body multipleClass: MultipleClassNetModel.Request
    ): Single<MultipleClassNetModel.Response>

    @POST("/api/v1/multiple_class/delete/{id}")
    fun deleteMultipleClass(@Path("id") multipleClassId: Int): Completable

    /*Note*/

    @POST("/api/v1/notes/create")
    fun createNote(@Body note: NoteNetModel.Request): Single<NoteNetModel.Response>

    @GET("/api/v1/notes/")
    fun getNotes(): Single<List<NoteNetModel.Response>>

    companion object{
        const val BASE_URL = "http://192.168.0.103:8080/"
    }
}