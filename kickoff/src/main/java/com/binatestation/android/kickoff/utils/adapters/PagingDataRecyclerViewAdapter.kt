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
 * Last Updated at 25/5/20 6:38 PM.
 */

package com.binatestation.android.kickoff.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.utils.Constants
import com.binatestation.android.kickoff.utils.adapters.holders.BaseViewHolder
import com.binatestation.android.kickoff.utils.adapters.holders.EmptyStateViewHolder
import com.binatestation.android.kickoff.utils.listeners.ItemClickListener
import com.binatestation.android.kickoff.utils.listeners.ViewBinder

/**
 * A general paging Recycler view adapter.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class PagingDataRecyclerViewAdapter<DataModelType : Any>(
    comparator: DiffUtil.ItemCallback<DataModelType>
) : PagingDataAdapter<DataModelType, RecyclerView.ViewHolder>(comparator), ItemClickListener {

    private var onClickItem: ((`object`: Any?, position: Int, actionView: View) -> Unit)? = null

    override fun setOnItemClickListener(onClickItem: (`object`: Any?, position: Int, actionView: View) -> Unit) {
        this.onClickItem = onClickItem
    }

    val itemViewTypeModels = ArrayList<ItemViewTypeModel<*, *, *>>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewBinder) {
            val viewBinder = holder as ViewBinder
            viewBinder.bindView(getItem(position) as? Any?)
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
                if(position != RecyclerView.NO_POSITION)
                    onClickItem?.let { onClick -> onClick(getItem(position), position, actionView) }
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
            onClickItem?.let { onClick -> onClick(getItem(position), position, actionView) }
        }
        return viewHolder
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val itemViewTypeModel = itemViewTypeModels.find {
            item?.javaClass?.name == it.clsType?.name
        }
        return if (itemViewTypeModel is ItemViewTypeModel) {
            itemViewTypeModel.layoutId
        } else
            EmptyStateViewHolder.LAYOUT
    }

}
