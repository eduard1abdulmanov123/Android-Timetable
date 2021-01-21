package abdulmanov.eduard.timetable.data.remote

import abdulmanov.eduard.timetable.data.remote.models.NoteNetModel
import abdulmanov.eduard.timetable.data.remote.models.UserNetModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface TimetableApi {

    @POST("api/v1/auth/sing-up")
    fun signUp(@Body user: UserNetModel.Request): Single<UserNetModel.Response>

    @POST("api/v1/auth/sing-in")
    fun signIn(@Body user: UserNetModel.Request): Single<UserNetModel.Response>

    @POST("/api/v1/notes/create")
    fun createNote(@Body note: NoteNetModel.Request): Single<NoteNetModel.Response>

    companion object{
        const val BASE_URL = "http://192.168.0.103:8080/"
    }
}