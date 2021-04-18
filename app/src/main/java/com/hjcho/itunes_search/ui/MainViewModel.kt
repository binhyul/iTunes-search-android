package com.hjcho.itunes_search.ui

import androidx.lifecycle.*
import com.hjcho.itunes_search.R
import com.hjcho.itunes_search.domain.song.GetSongListUseCase
import com.hjcho.itunes_search.util.RecyclerItem
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.hjcho.itunes_search.BR
import com.hjcho.itunes_search.data.db.SongModel
import com.hjcho.itunes_search.domain.song.OnCheckedFavoriteTrackUseCase
import com.hjcho.itunes_search.domain.song.SongListModel
import com.hjcho.itunes_search.util.RecyclerItemComparator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(
    private val getSongListUseCase: GetSongListUseCase,
    private val onCheckedFavoriteTrackUseCase: OnCheckedFavoriteTrackUseCase
) : ViewModel(), SongActionListener {

    private val _songItems: MutableLiveData<List<SongViewItem>> = MutableLiveData()
    val songItems: LiveData<List<RecyclerItem>> = Transformations.map(_songItems) { list ->
        list.map {
            it.toRecyclerItem(this)
        }
    }

    private val _favoriteSongItems: MutableLiveData<List<SongViewItem>> = MutableLiveData()
    val favoriteSongItems: LiveData<List<RecyclerItem>> =
        Transformations.map(_favoriteSongItems) { list ->
            list.map {
                it.toRecyclerItem(this)
            }
        }

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

    override fun onCheckFavorite(trackId: Int, isChecked: Boolean) {
        _songItems.value = _songItems.value?.map { song ->
            if (song.songModel.trackId == trackId) {
                val newSongItem = song.copy(favorite = isChecked)
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
    fun onCheckFavorite(trackId: Int, isChecked: Boolean)
}

data class SongViewItem(
    val songModel: SongModel,
    val favorite: Boolean
) : RecyclerItemComparator {

    override fun isSameContent(other: Any): Boolean {
        return favorite == (other as SongViewItem).favorite
    }

    override fun isSameItem(other: Any): Boolean {
        return songModel.trackId == (other as SongViewItem).songModel.trackId
    }

}


fun SongViewItem.toRecyclerItem(actionListener: SongActionListener) =
    RecyclerItem(
        listOf(BR.model to this, BR.actionListener to actionListener), R.layout.song_item
    )


