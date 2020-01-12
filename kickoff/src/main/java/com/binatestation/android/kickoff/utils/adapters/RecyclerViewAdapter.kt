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
 * Last Updated at 12/1/20 3:40 PM.
 */

package com.binatestation.android.kickoff.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.repository.models.EmptyStateModel
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.utils.adapters.holders.EmptyStateViewHolder
import com.binatestation.android.kickoff.utils.listeners.AdapterListener
import com.binatestation.android.kickoff.utils.listeners.OnListItemClickListener
import com.binatestation.android.kickoff.utils.listeners.ViewBinder


/**
 * Created by RKR on 7/13/2017.
 * RecyclerViewAdapter
 */

open class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AdapterListener {
    override fun getType(): Int {
        return type ?: 0
    }

    var type: Int? = 0
    var showEmptyState: Boolean = true
    internal var data: ArrayList<Any>? = null
    val itemViewTypeModels = ArrayList<ItemViewTypeModel<*, *, *>>()


    /**
     * the listener object to have list item click
     */
    override var clickListener: OnListItemClickListener? = null

    init {
        data = EmptyStateModel.loadingDataModels
    }

    @Suppress("unused")
    fun getData(): ArrayList<*>? {
        return data
    }

    @Suppress("unused")
            /**
             * sets the data to the adapter class
             *
             * @param data ArrayList of any Model class
             */
    fun setData(data: ArrayList<Any>) {
        if (data.size > 0) {
            this.data = data
        } else {
            this.data = if (showEmptyState) EmptyStateModel.emptyDataModels else data
        }
        notifyDataSetChanged()
    }

    /**
     * sets the data to the adapter class
     *
     * @param data ArrayList of any Model class
     */
    fun setTypedData(data: List<*>?) {
        data?.let {
            val objects = ArrayList<Any>(it)
            if (it.isNotEmpty()) {
                this.data = objects
            } else {
                this.data = if (showEmptyState) EmptyStateModel.emptyDataModels else objects
            }
            notifyDataSetChanged()
        }
    }

    fun add(`object`: Any) {
        if (this.data?.remove(EmptyStateModel.unKnownEmptyModel) == true) {
            notifyDataSetChanged()
        }
        this.data?.add(`object`)
        notifyItemInserted(data?.size ?: 1 - 1)
    }

    fun add(`object`: Any, position: Int) {
        if (this.data?.remove(EmptyStateModel.unKnownEmptyModel) == true) {
            notifyDataSetChanged()
        }
        if (position < data?.size ?: 0) {
            this.data?.add(position, `object`)
            notifyItemInserted(position)
        }
    }

    @Suppress("unused")
            /**
             * removes item from specified position
             *
             * @param position position of the item to remove
             */
    fun removeItem(position: Int) {
        if (position < data?.size ?: 0 && position >= 0) {
            this.data?.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItem(position: Int): Any {
        return data?.takeIf { it.size > position && position >= 0 }?.let { it[position] }
            ?: Any()
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val itemViewTypeModel = itemViewTypeModels.find {
            item.javaClass.name == it.clsType?.name
        }
        return if (itemViewTypeModel is ItemViewTypeModel) {
            itemViewTypeModel.layoutId
        } else
            EmptyStateViewHolder.LAYOUT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemViewType =
            itemViewTypeModels.find { itemViewTypeModel -> itemViewTypeModel.layoutId == viewType }
        val constructor = itemViewType?.viewHolder?.getConstructor(
            AdapterListener::class.java,
            itemViewType.viewDataBindingType
        )
        try {
            return constructor?.newInstance(
                this, DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    itemViewType.layoutId, parent, false
                )
            ) as RecyclerView.ViewHolder
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return EmptyStateViewHolder(parent, this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewBinder) {
            val viewBinder = holder as ViewBinder
            viewBinder.bindView(getItem(position))
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}

