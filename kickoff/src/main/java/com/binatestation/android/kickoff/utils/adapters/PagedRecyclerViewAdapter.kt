/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last Updated at 8/4/20 6:48 PM.
 */

package com.binatestation.android.kickoff.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.repository.models.EmptyStateModel
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.repository.models.NetworkState
import com.binatestation.android.kickoff.utils.adapters.holders.BaseViewHolder
import com.binatestation.android.kickoff.utils.adapters.holders.EmptyStateViewHolder
import com.binatestation.android.kickoff.utils.listeners.ItemClickListener
import com.binatestation.android.kickoff.utils.listeners.ViewBinder

/**
 * A general paged Recycler view adapter.
 */
class PagedRecyclerViewAdapter<DataModelType>(
    comparator: DiffUtil.ItemCallback<DataModelType>
) : PagedListAdapter<DataModelType, RecyclerView.ViewHolder>(comparator), ItemClickListener {

    private var onClickItem: ((`object`: Any?, position: Int, actionView: View) -> Unit)? = null

    override fun setOnItemClickListener(onClickItem: (`object`: Any?, position: Int, actionView: View) -> Unit) {
        this.onClickItem = onClickItem
    }

    private var networkState: NetworkState? = null

    @Suppress("MemberVisibilityCanBePrivate", "unused")
    var showEmptyState: Boolean = true
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
        viewHolder = EmptyStateViewHolder(parent)
        viewHolder.setOnItemClickListener { position, actionView ->
            onClickItem?.let { onClick -> onClick(getItemData(position), position, actionView) }
        }
        return viewHolder
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    private fun getItemData(position: Int): Any? {
        if (hasExtraRow() && position == itemCount - 1) {
            return EmptyStateModel.loadingDataModels[0]
        }
        return getItem(position)
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