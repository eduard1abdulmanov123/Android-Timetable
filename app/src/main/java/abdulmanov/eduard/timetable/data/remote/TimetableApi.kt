package abdulmanov.eduard.timetable.data.remote

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

    @POST("/api/v1/notes/create")
    fun createNote(@Body note: NoteNetModel.Request): Single<NoteNetModel.Response>

    @GET("/api/v1/notes/")
    fun getNotes(): Single<List<NoteNetModel.Response>>

    companion object{
        const val BASE_URL = "http://192.168.0.103:8080/"
    }
}