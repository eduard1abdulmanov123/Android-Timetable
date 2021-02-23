package abdulmanov.eduard.timetable.data.remote.helpers

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RefreshTokenInterceptor(
    private val sharedPreferences: AuthSharedPreferences
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if(response.code == 401 && sharedPreferences.refreshToken != null){
            if(refreshToken()){
                val request = chain.request().newBuilder()
                    .header("Authorization", sharedPreferences.token ?: "")
                    .build()

                return chain.proceed(request)
            }
        }

        return response
    }

    private fun refreshToken(): Boolean {
        val okHttpClient = OkHttpClient()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonRequest = JSONObject().apply { put("refreshToken", sharedPreferences.refreshToken ?: "") }

        val request = Request.Builder()
            .url(TimetableApi.BASE_URL + "api/v1/auth/refreshToken")
            .post(jsonRequest.toString().toRequestBody(mediaType))
            .build()

        val response = okHttpClient.newCall(request).execute()

        return if(response.code == 200){
            val jsonResponse = JSONObject(response.body!!.string())
            sharedPreferences.token = jsonResponse.getString("token")
            sharedPreferences.refreshToken = jsonResponse.getString("refreshToken")
            true
        }else{
            false
        }
    }
}