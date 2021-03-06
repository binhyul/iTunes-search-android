package com.hjcho.itunes_search.data.db

import androidx.room.*

const val FAVORITE_TRACK_TABLE = "favorite_track_table"

@Entity(tableName = FAVORITE_TRACK_TABLE)
data class SongModel(
    @PrimaryKey val trackId: Int,
    val trackName: String,
    val artistName: String,
    val collectionName: String,
    val artworkUrl60: String
)

@Dao
interface FavoriteTrackListDao {

    @Insert
    fun insertSong(songModel: SongModel)

    @Update
    fun updateSong(songModel: SongModel)

    @Delete
    fun deleteSong(songModel: SongModel)

    @Query("SELECT * from $FAVORITE_TRACK_TABLE")
    fun getTrackList(): List<SongModel>

}

