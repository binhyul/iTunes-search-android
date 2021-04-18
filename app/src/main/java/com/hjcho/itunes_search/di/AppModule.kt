package com.hjcho.itunes_search.di

import android.content.Context
import androidx.room.Room
import com.hjcho.itunes_search.MainApplication
import com.hjcho.itunes_search.data.db.APP_ROOM_DATABASE_NAME
import com.hjcho.itunes_search.data.db.AppRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }


    @Provides
    @Singleton
    fun provideFavoriteTrackListDao(database: AppRoomDatabase) = database.favoriteTrackListDao()

    @Provides
    @Singleton
    fun provideAppRoomDatabase(context: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppRoomDatabase::class.java,
            APP_ROOM_DATABASE_NAME
        ).build()
    }

}