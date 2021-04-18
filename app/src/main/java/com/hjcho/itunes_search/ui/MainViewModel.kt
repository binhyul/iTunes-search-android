package com.hjcho.itunes_search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjcho.itunes_search.data.db.SongModel
import com.hjcho.itunes_search.domain.song.GetSongListUseCase
import com.hjcho.itunes_search.domain.song.OnCheckedFavoriteTrackUseCase
import com.hjcho.itunes_search.domain.song.SongListModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(
    private val getSongListUseCase: GetSongListUseCase,
    private val onCheckedFavoriteTrackUseCase: OnCheckedFavoriteTrackUseCase
) : ViewModel(), SongActionListener {

    private val _songItems: MutableLiveData<List<SongViewItem>> = MutableLiveData()
    val songItems: LiveData<List<SongViewItem>> = _songItems

    private val _favoriteSongItems: MutableLiveData<List<SongViewItem>> = MutableLiveData()
    val favoriteSongItems: LiveData<List<SongViewItem>> = _favoriteSongItems


    init {
        viewModelScope.launch {
            with(getSongListUseCase(Unit)) {
                collect { result ->
                    when (result) {
                        is SongListModel.TrackListModel -> _songItems.value = result.tracks
                        is SongListModel.FavoriteListModel -> _favoriteSongItems.value =
                            result.favoriteTracks
                    }
                }
            }
        }

    }

    override fun onCheckFavorite(trackId: Int) {
        _songItems.value = _songItems.value?.map { song ->
            if (song.songModel.trackId == trackId) {
                val newSongItem = song.copy(favorite = !song.favorite)
                onCheckedFavoriteTrack(newSongItem)
                newSongItem
            } else {
                song
            }
        }
    }


    private fun onCheckedFavoriteTrack(songViewItem: SongViewItem) {
        viewModelScope.launch {
            with(onCheckedFavoriteTrackUseCase(songViewItem)) {
                _favoriteSongItems.value = data
            }
        }
    }


}

interface SongActionListener {
    fun onCheckFavorite(trackId: Int)
}

data class SongViewItem(
    val songModel: SongModel,
    val favorite: Boolean
)
