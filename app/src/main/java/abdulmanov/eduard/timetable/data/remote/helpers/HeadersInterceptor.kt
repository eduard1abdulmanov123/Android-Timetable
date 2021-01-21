package abdulmanov.eduard.timetable.data.remote.helpers

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(
    private val sharedPreferences: AuthSharedPreferences
): Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", sharedPreferences.getToken() ?: "")
            .build()

        return chain.proceed(request)
    }
}