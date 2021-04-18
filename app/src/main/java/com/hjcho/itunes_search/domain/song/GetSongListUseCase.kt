package com.hjcho.itunes_search.domain.song

import com.hjcho.itunes_search.data.SongRepository
import com.hjcho.itunes_search.domain.FlowUseCase
import com.hjcho.itunes_search.ui.SongViewItem
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetSongListUseCase @Inject constructor(
    private val songRepository: SongRepository
) : FlowUseCase<Unit, SongListModel>() {
    override suspend fun execute(parameters: Unit): Flow<SongListModel> {
        return flow {
            withContext(currentCoroutineContext()) {
                val favoriteItemCall = async { songRepository.getFavoriteSongs() }
                val songItemCall = async { songRepository.getSongs() }
                val favoriteItem = favoriteItemCall.await()
                emit(
                    SongListModel.FavoriteListModel(favoriteItem.map { favorite ->
                        SongViewItem(favorite, true)
                    })
                )

                val songItem = songItemCall.await().results
                val trackList = songItem.map { song ->
                    val isFavorite =
                        favoriteItem.find { favorite -> favorite.trackId == song.trackId } != null

                    SongViewItem(
                        song, isFavorite
                    )
                }

                emit(SongListModel.TrackListModel(trackList))
            }
        }
    }
}

sealed class SongListModel {
    data class TrackListModel(
        val tracks: List<SongViewItem>
    ) : SongListModel()

    data class FavoriteListModel(
        val favoriteTracks: List<SongViewItem>
    ) : SongListModel()
}


