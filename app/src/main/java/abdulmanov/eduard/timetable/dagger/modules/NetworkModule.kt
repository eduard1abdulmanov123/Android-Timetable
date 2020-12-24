package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.remote.RetrofitFactory
import abdulmanov.eduard.timetable.data.remote.TimetableApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return RetrofitFactory.getOkHttpInstance()
    }

    @Singleton
    @Provides
    fun provideTimetableApi(okHttpClient: OkHttpClient): TimetableApi {
        return RetrofitFactory.getTimetableApi(TimetableApi.BASE_URL, okHttpClient)
    }

}