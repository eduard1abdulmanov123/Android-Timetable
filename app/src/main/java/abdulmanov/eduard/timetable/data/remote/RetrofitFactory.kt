package abdulmanov.eduard.timetable.data.remote

import abdulmanov.eduard.timetable.data.local.sharedpreferences.AuthSharedPreferences
import abdulmanov.eduard.timetable.data.remote.helpers.HeadersInterceptor
import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {

        fun getOkHttpInstance(sharedPreferences: AuthSharedPreferences): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(HeadersInterceptor(sharedPreferences))
                .build()
        }

        fun getTimetableApi(baseUrl: String, okHttpClient: OkHttpClient): TimetableApi {
            return getRetrofitInstance(baseUrl, okHttpClient).create(TimetableApi::class.java)
        }

        private fun getRetrofitInstance(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}