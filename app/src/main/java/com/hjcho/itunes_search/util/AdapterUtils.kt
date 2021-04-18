package com.hjcho.itunes_search.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable
import com.hjcho.itunes_search.BR

class BindingViewHolder(
    val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun updateBinding(variableId: Int, value: Any) {
        binding.setVariable(variableId, value)
    }
}


data class RecyclerItem(
    val bindings: List<Pair<Int, Any>>,
    @LayoutRes val layoutId: Int
) : Serializable {
    fun bind(binding: ViewDataBinding) {
        bindings.forEach { (variableId, data) ->
            binding.setVariable(variableId, data)
        }
    }
}

/**
 *
 * RecyclerViewDiffAdapter
 */

class RecyclerViewDiffAdapter : ListAdapter<RecyclerItem, BindingViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)

        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).bind(holder.binding)
        holder.binding.executePendingBindings()
    }

}


class DiffCallback : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(
        oldItem: RecyclerItem,
        newItem: RecyclerItem
    ): Boolean {
        val oldData = oldItem.bindings.find { it.first == BR.model }?.second
        val newData = newItem.bindings.find { it.first == BR.model }?.second
        return if (oldData is RecyclerItemComparator
            && newData is RecyclerItemComparator
        ) {
            oldData.isSameItem(newData)
        } else oldData == newData
    }

    override fun areContentsTheSame(
        oldItem: RecyclerItem,
        newItem: RecyclerItem
    ): Boolean {
        val oldData = oldItem.bindings.find { it.first == BR.model }?.second
        val newData = newItem.bindings.find { it.first == BR.model }?.second
        return if (oldData is RecyclerItemComparator
            && newData is RecyclerItemComparator
        ) {
            oldData.isSameContent(newData)
        } else true
    }
}


@BindingAdapter("android:items")
fun setRecyclerViewItemsUseDiff(
    recyclerView: RecyclerView,
    items: List<RecyclerItem>?
) {
    var adapter = (recyclerView.adapter as? RecyclerViewDiffAdapter)
    recyclerView.itemAnimator = null
    if (adapter == null) {
        adapter = RecyclerViewDiffAdapter()
        recyclerView.adapter = adapter
    }
    adapter.submitList(items.orEmpty())
}


interface RecyclerItemComparator {
    fun isSameItem(other: Any): Boolean
    fun isSameContent(other: Any): Boolean
}


