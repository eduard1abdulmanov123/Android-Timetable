package abdulmanov.eduard.timetable.dagger.modules

import abdulmanov.eduard.timetable.data.mappers.TimetableMapperDomain
import abdulmanov.eduard.timetable.data.mappers.UserMapperDomain
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperDomainModule {

    @Singleton
    @Provides
    fun provideUserMapperDomain(): UserMapperDomain {
        return UserMapperDomain()
    }

    @Singleton
    @Provides
    fun provideTimetableMapperDomain(): TimetableMapperDomain {
        return TimetableMapperDomain()
    }
}