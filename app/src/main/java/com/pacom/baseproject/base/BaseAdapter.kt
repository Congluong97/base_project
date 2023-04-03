package com.pacom.baseproject.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T : Any, VB : ViewDataBinding>(
    val context: Context,
    @LayoutRes val layoutID: Int,
    private val bindingInterface: GenericSimpleRecyclerBindingInterface<T, VB>,
    private val onItemClickListener: ((T)->Unit)? = null
) : ListAdapter<T, BaseAdapter.ViewHolder>(GenericDiffUtilCallback()) {

    var selectionTracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            layoutID,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(
            item,
            bindingInterface,
            onItemClickListener,
            position,
            selectionTracker?.isSelected(position.toLong()) ?: false
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int) = position

    class ViewHolder(var viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun <T : Any, VB: ViewDataBinding> bind(
            item: T,
            bindingInterface: GenericSimpleRecyclerBindingInterface<T, VB>,
            onItemClickListener: ((T)->Unit)?,
            position: Int,
            isActive: Boolean
        ) {
            bindingInterface.bindView(item, viewBinding as VB, onItemClickListener, position)
            if(isActive) {
                itemView.background = ColorDrawable(Color.parseColor("#80deea"))
            } else {
                itemView.background = ColorDrawable(Color.WHITE)
            }
        }

        fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int {
                return bindingAdapterPosition
            }

            override fun getSelectionKey(): Long {
                return itemId
            }
        }
    }
}

class GenericDiffUtilCallback<T: Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}

interface GenericSimpleRecyclerBindingInterface<T : Any, VB : ViewDataBinding> {
    fun bindView(item: T, view: VB, onItemClick: ((T)->Unit)?, position: Int)
}

class GenericItemKeyProvider(private val recyclerView: RecyclerView) : ItemKeyProvider<Long>(
    SCOPE_MAPPED
) {
    override fun getKey(position: Int): Long? {
        return recyclerView.adapter?.getItemId(position)
    }

    override fun getPosition(key: Long): Int {
        return recyclerView.findViewHolderForItemId(key)?.layoutPosition ?: RecyclerView.NO_POSITION
    }
}

class GenericItemLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        return if (view != null) {
            (recyclerView.getChildViewHolder(view) as BaseAdapter.ViewHolder).getItemDetails()
        } else {
            null
        }
    }
}