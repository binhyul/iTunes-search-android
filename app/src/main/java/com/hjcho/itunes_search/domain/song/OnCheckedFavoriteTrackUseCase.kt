package com.hjcho.itunes_search.domain.song

import com.hjcho.itunes_search.data.SongRepository
import com.hjcho.itunes_search.domain.CompletableUseCase
import com.hjcho.itunes_search.ui.SongViewItem
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import kotlin.time.ExperimentalTime


class OnCheckedFavoriteTrackUseCase @Inject constructor(
    private val songRepository: SongRepository
) : CompletableUseCase<SongViewItem, List<SongViewItem>>() {
    @ExperimentalTime
    override suspend fun CoroutineScope.execute(parameters: SongViewItem): List<SongViewItem> {
        songRepository.checkedFavoriteSong(parameters.songModel, parameters.favorite)
        return songRepository.getFavoriteSongs().map { song ->
            SongViewItem(song, true)
        }
    }
}