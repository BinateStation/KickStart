/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("DEPRECATION")

package com.binatestation.android.kickoff.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.repository.models.EmptyStateModel
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.repository.models.NetworkState
import com.binatestation.android.kickoff.repository.models.enums.Status
import com.binatestation.android.kickoff.utils.adapters.holders.BaseViewHolder
import com.binatestation.android.kickoff.utils.adapters.holders.EmptyStateViewHolder
import com.binatestation.android.kickoff.utils.listeners.ItemClickListener
import com.binatestation.android.kickoff.utils.listeners.ViewBinder

/**
 * A general paged Recycler view adapter.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
@Deprecated(
    message = "PagedRecyclerViewAdapter is deprecated and has been replaced by PagingDataRecyclerViewAdapter",
    replaceWith = ReplaceWith(
        "PagingDataRecyclerViewAdapter<DataModelType>",
        "com.binatestation.android.kickoff.utils.adapters.PagingDataRecyclerViewAdapter"
    )
)
class PagedRecyclerViewAdapter<DataModelType : Any>(
    comparator: DiffUtil.ItemCallback<DataModelType>
) : PagedListAdapter<DataModelType, RecyclerView.ViewHolder>(comparator), ItemClickListener {

    private var onClickItem: ((`object`: Any?, position: Int, actionView: View) -> Unit)? = null

    override fun setOnItemClickListener(onClickItem: (`object`: Any?, position: Int, actionView: View) -> Unit) {
        this.onClickItem = onClickItem
    }

    @Deprecated("Removed from here and added at fragment level")
    private var networkState: NetworkState? = null

    var showEmptyState: Boolean = true
    var emptyStateModel: EmptyStateModel? = null
    val itemViewTypeModels = ArrayList<ItemViewTypeModel<*, *, *>>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewBinder) {
            val viewBinder = holder as ViewBinder
            viewBinder.bindView(getItemData(position) as Any)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: BaseViewHolder?
        val itemViewType =
            itemViewTypeModels.find { itemViewTypeModel -> itemViewTypeModel.layoutId == viewType }
        val constructor = itemViewType?.viewHolder?.getConstructor(
            itemViewType.viewDataBindingType
        )
        try {
            viewHolder = constructor?.newInstance(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    itemViewType.layoutId, parent, false
                )
            ) as BaseViewHolder?
            viewHolder?.setOnItemClickListener { position, actionView ->
                onClickItem?.let { onClick -> onClick(getItemData(position), position, actionView) }
            }
            viewHolder?.let { return it }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        viewHolder = EmptyStateViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_empty_state, parent, false
            )
        )
        viewHolder.setOnItemClickListener { position, actionView ->
            onClickItem?.let { onClick -> onClick(getItemData(position), position, actionView) }
        }
        return viewHolder
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    private fun getItemData(position: Int): Any? {
        if (hasExtraRow() && position == itemCount - 1) {
            return getEmptyStateModel()
        }
        return getItem(position)
    }

    private fun getEmptyStateModel(): Any {
        return emptyStateModel ?: getEmptyStateModelFromNetworkState()
    }

    private fun getEmptyStateModelFromNetworkState(): EmptyStateModel {
        return networkState?.takeIf { it.status == Status.RUNNING }
            ?.let { EmptyStateModel.loadingDataModel }
            ?: networkState?.takeIf { it.status == Status.NO_DATA }
                ?.let { EmptyStateModel.emptyDataModel }
            ?: networkState?.takeIf { it.status == Status.NO_INTERNET }
                ?.let { EmptyStateModel.noInternetEmptyModel }
            ?: networkState?.takeIf { it.status == Status.FAILED }
                ?.let { EmptyStateModel(message = it.msg) }
            ?: EmptyStateModel.unKnownEmptyModel
    }

    override fun getItemViewType(position: Int): Int {
        if (hasExtraRow() && position == itemCount - 1) {
            return EmptyStateViewHolder.LAYOUT
        }
        val item = getItemData(position)
        val itemViewTypeModel = itemViewTypeModels.find {
            item?.javaClass?.name == it.clsType?.name
        }
        return if (itemViewTypeModel is ItemViewTypeModel) {
            itemViewTypeModel.layoutId
        } else
            EmptyStateViewHolder.LAYOUT
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    @Deprecated("Removed from here and added at fragment level")
    fun setNetworkState(newNetworkState: NetworkState?) {
        try {
            val previousState = this.networkState
            val hadExtraRow = hasExtraRow()
            this.networkState = newNetworkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && previousState != newNetworkState) {
                notifyItemChanged(itemCount - 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
