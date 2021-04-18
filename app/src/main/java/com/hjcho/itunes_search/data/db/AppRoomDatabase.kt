package com.hjcho.itunes_search.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        SongModel::class
    ],
    version = 1
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun favoriteTrackListDao(): FavoriteTrackListDao
}

const val APP_ROOM_DATABASE_NAME = "app_room_database"
