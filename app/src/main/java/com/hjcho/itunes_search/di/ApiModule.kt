package com.hjcho.itunes_search.di

import com.hjcho.itunes_search.data.ApiFactory
import com.hjcho.itunes_search.data.SongService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideTodayHouseApi(factory: ApiFactory): SongService {
        return factory.songApi
    }


}
