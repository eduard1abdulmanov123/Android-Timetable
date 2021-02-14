package abdulmanov.eduard.timetable.data.remote

import abdulmanov.eduard.timetable.data.remote.models.*
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

    @POST("/api/v1/multiple_class/create")
    fun createMultipleClass(@Body multipleClass: MultipleClassNetModel.Request): Single<MultipleClassNetModel.Response>

    @POST("/api/v1/multiple_class/update/{id}")
    fun updateMultipleClass(
        @Path("id") multipleClassId: Int,
        @Body multipleClass: MultipleClassNetModel.Request
    ): Single<MultipleClassNetModel.Response>

    @POST("/api/v1/multiple_class/delete/{id}")
    fun deleteMultipleClass(@Path("id") multipleClassId: Int): Completable

    /*OneTimeClass*/

    @POST("/api/v1/onetime_class/create")
    fun createOneTimeClass(@Body oneTimeClass: OneTimeClassNetModel.Request): Single<OneTimeClassNetModel.Response>

    @POST("/api/v1/onetime_class/update/{id}")
    fun updateOneTimeClass(
        @Path("id") oneTimeClassId: Int,
        @Body oneTimeClass: OneTimeClassNetModel.Request
    ): Single<OneTimeClassNetModel>

    @POST("/api/v1/onetime_class/delete/{id}")
    fun deleteOneTimeClass(@Path("id") oneTimeClassId: Int): Completable

    /*Note*/

    @POST("/api/v1/notes/create")
    fun createNote(@Body note: NoteNetModel.Request): Single<NoteNetModel.Response>

    @POST("/api/v1/notes/update/{id}")
    fun updateNote(
        @Path("id") noteId: Int,
        @Body noteNetModel: NoteNetModel.Request
    ): Single<NoteNetModel.Response>

    @POST("/api/v1/notes/delete/{id}")
    fun deleteNote(@Path("id") noteId: Int): Completable

    companion object{
        const val BASE_URL = "http://192.168.0.103:8080/"
    }
}