package abdulmanov.eduard.timetable.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {

        fun getOkHttpInstance(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
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