package com.hjcho.itunes_search.data

import com.hjcho.itunes_search.data.db.FavoriteTrackListDao
import com.hjcho.itunes_search.data.db.SongModel
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SongRepository @Inject constructor(
    private val api: SongService,
    private val favoriteTrackListDao: FavoriteTrackListDao
) {

    suspend fun getSongs() = api.getSongs()

    suspend fun getFavoriteSongs() = favoriteTrackListDao.getTrackList()

    suspend fun checkedFavoriteSong(songModel: SongModel, favorite: Boolean) =
        if (favorite) {
            favoriteTrackListDao.insertSong(songModel)
        } else {
            favoriteTrackListDao.deleteSong(songModel)
        }

    suspend fun updateFavoriteSong(songModel: SongModel) =
        favoriteTrackListDao.updateSong(songModel)

}

interface SongService {

    @GET("search?term=greenday&entity=song")
    suspend fun getSongs(): SongResponseModel
}


data class SongResponseModel(
    val resultCount: Int,
    val results: List<SongModel>
)


