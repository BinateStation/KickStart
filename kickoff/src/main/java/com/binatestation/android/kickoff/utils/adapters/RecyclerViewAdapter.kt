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
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.repository.models.EmptyStateModel
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.utils.adapters.holders.BaseViewHolder
import com.binatestation.android.kickoff.utils.adapters.holders.EmptyStateViewHolder
import com.binatestation.android.kickoff.utils.listeners.ItemClickListener
import com.binatestation.android.kickoff.utils.listeners.ViewBinder


/**
 * Created by RKR on 7/13/2017.
 * RecyclerViewAdapter
 */

open class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemClickListener {

    private var onClickItem: ((`object`: Any?, position: Int, actionView: View) -> Unit)? = null

    override fun setOnItemClickListener(onClickItem: (`object`: Any?, position: Int, actionView: View) -> Unit) {
        this.onClickItem = onClickItem
    }

    @Suppress("MemberVisibilityCanBePrivate")
    var showEmptyState: Boolean = true
    internal var data: ArrayList<Any>? = null
    val itemViewTypeModels = ArrayList<ItemViewTypeModel<*, *, *>>()

    init {
        data = EmptyStateModel.loadingDataModels
    }

    @Suppress("unused")
    fun getData(): ArrayList<*>? {
        return data
    }

    /**
     * sets the data to the adapter class
     *
     * @param data ArrayList of any Model class
     */
    @Suppress("unused")
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
            val objects = ArrayList(it.filterIsInstance(Any::class.java))
            if (objects.isNotEmpty()) {
                this.data = objects
            } else {
                this.data = if (showEmptyState) EmptyStateModel.emptyDataModels else objects
            }
            notifyDataSetChanged()
        }
    }

    /**
     * add the object in to the item list
     * @param `object` Any item to add
     */
    fun add(`object`: Any) {
        if (this.data?.remove(EmptyStateModel.unKnownEmptyModel) == true) {
            notifyDataSetChanged()
        }
        this.data?.add(`object`)
        notifyItemInserted(data?.size ?: 1 - 1)
    }

    /**
     * adds all elements to the item list
     *
     * @param data list of items
     */
    @Suppress("unused")
    fun addAll(data: List<*>?) {
        data?.let {
            if (this.data?.remove(EmptyStateModel.unKnownEmptyModel) == true) {
                notifyDataSetChanged()
            }
            val collection = ArrayList(it).filterIsInstance(Any::class.java)
            this.data?.addAll(collection)
            notifyDataSetChanged()
        }
    }

    /**
     * adds the item in to the given position
     * @param `object` Any item to add
     * @param position Int the position to add
     */
    fun add(`object`: Any, position: Int) {
        if (this.data?.remove(EmptyStateModel.unKnownEmptyModel) == true) {
            notifyDataSetChanged()
        }
        if (position < data?.size ?: 0) {
            this.data?.add(position, `object`)
            notifyItemInserted(position)
        }
    }

    /**
     * removes item from specified position
     * @param position Int position of the item to remove
     */
    @Suppress("unused")
    fun removeItem(position: Int) {
        if (position < data?.size ?: 0 && position >= 0) {
            this.data?.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     * get item from position
     * @param position Int the item position
     * @return Any the selected item
     */
    private fun getItem(position: Int): Any? {
        return data?.takeIf { it.size > position && position >= 0 }?.let { it[position] }
            ?: Any()
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

