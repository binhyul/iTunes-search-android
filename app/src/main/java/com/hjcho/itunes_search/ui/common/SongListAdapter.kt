package com.hjcho.itunes_search.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hjcho.itunes_search.databinding.SongItemBinding
import com.hjcho.itunes_search.ui.SongActionListener
import com.hjcho.itunes_search.ui.SongViewItem


internal class SongListAdapter(
    private val actionListener: SongActionListener
) : ListAdapter<SongViewItem, SongModelHolder>(
    SongModelDiff
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongModelHolder {
        return SongModelHolder(
            SongItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), actionListener
        )
    }

    override fun onBindViewHolder(holder: SongModelHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

}

internal object SongModelDiff :
    DiffUtil.ItemCallback<SongViewItem>() {

    override fun areItemsTheSame(
        oldItem: SongViewItem,
        newItem: SongViewItem
    ): Boolean {
        return oldItem.songModel.trackId == newItem.songModel.trackId
    }


    override fun areContentsTheSame(
        oldItem: SongViewItem,
        newItem: SongViewItem
    ): Boolean {
        return oldItem.favorite == newItem.favorite
    }

}


internal class SongModelHolder(
    private val binding: SongItemBinding,
    private val actionListener: SongActionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: SongViewItem) {
        binding.model = data
        binding.actionListener = actionListener
    }
}


@BindingAdapter("android:items", "android:song_action_listener")
fun setRecyclerViewPagedCardItems(
    recyclerView: RecyclerView,
    items: List<SongViewItem>?,
    actionListener: SongActionListener
) {
    var adapter = (recyclerView.adapter as? SongListAdapter)
    recyclerView.itemAnimator = null
    if (adapter == null) {
        adapter = SongListAdapter(actionListener)
        recyclerView.adapter = adapter
    }

    adapter.submitList(items)
}
