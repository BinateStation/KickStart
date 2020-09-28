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
 * Last Updated at 8/4/20 7:52 PM.
 */

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.databinding.AdapterEmptyStateBinding
import com.binatestation.android.kickoff.repository.models.EmptyStateModel
import com.binatestation.android.kickoff.repository.models.NetworkState
import com.binatestation.android.kickoff.repository.models.enums.Status
import com.binatestation.android.kickoff.utils.adapters.NetworkLoadStateAdapter
import com.binatestation.android.kickoff.utils.adapters.PagedRecyclerViewAdapter
import com.binatestation.android.kickoff.utils.adapters.PagingDataRecyclerViewAdapter
import com.binatestation.android.kickoff.utils.adapters.holders.EmptyStateViewHolder
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [BaseListFragment] subclass. which can be used for recycler view with paging
 * Here we sets [PagedRecyclerViewAdapter] for the [RecyclerView] used in [BaseListFragment]
 */

open class PagingDataListFragment<DataModelType : Any>(private val comparator: DiffUtil.ItemCallback<DataModelType>) :
    BaseListFragment() {
    private var mAdapter: PagingDataRecyclerViewAdapter<DataModelType>? = null

    private var networkState: NetworkState? = null

    private var emptyStateViewHolder: EmptyStateViewHolder? = null

    /**
     * get [PagedRecyclerViewAdapter] object used in the [RecyclerView] of [BaseListFragment]
     */
    override val adapter: PagingDataRecyclerViewAdapter<DataModelType>
        get() {
            if (mAdapter == null) {
                mAdapter = PagingDataRecyclerViewAdapter(comparator = comparator)
            }
            return mAdapter!!
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRecyclerView()?.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NetworkLoadStateAdapter(),
            footer = NetworkLoadStateAdapter()
        )
        val adapterEmptyStateBinding = DataBindingUtil.bind<AdapterEmptyStateBinding>(
            empty_state
        )
        adapterEmptyStateBinding?.let { emptyStateViewHolder = EmptyStateViewHolder(it) }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        try {
            this.networkState = newNetworkState
            if (this.networkState == NetworkState.LOADED) {
                empty_state?.visibility = View.GONE
            } else {
                empty_state?.visibility = View.VISIBLE
            }
            getEmptyStateModelFromNetworkState().let {
                emptyStateViewHolder?.bindView(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

}