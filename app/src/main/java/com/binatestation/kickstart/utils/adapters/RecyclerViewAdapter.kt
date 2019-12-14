/*
 * Created By RKR
 * Last Updated at 14/12/19 4:38 PM.
 *
 * Copyright (c) 2019. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.binatestation.kickstart.utils.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.kickstart.repository.models.EmptyStateModel
import com.binatestation.kickstart.utils.adapters.holders.EmptyStateViewHolder
import com.binatestation.kickstart.utils.listeners.AdapterListener
import com.binatestation.kickstart.utils.listeners.OnListItemClickListener
import com.binatestation.kickstart.utils.listeners.ViewBinder
import java.util.*


/**
 * Created by RKR on 7/13/2017.
 * RecyclerViewAdapter
 */

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AdapterListener {
    override fun getType(): Int {
        return type ?: 0
    }

    var type: Int? = 0
    internal var showEmptyState: Boolean = true
    internal var data: ArrayList<Any>? = null


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
        with(getItem(position)) {
            when (this) {
                is EmptyStateModel -> return EmptyStateViewHolder.LAYOUT
                else -> return EmptyStateViewHolder.LAYOUT
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

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

