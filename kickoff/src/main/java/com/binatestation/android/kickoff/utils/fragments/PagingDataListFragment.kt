/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
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
        val adapterEmptyStateBinding = DataBindingUtil.bind<AdapterEmptyStateBinding>(
            empty_state
        )
        adapterEmptyStateBinding?.let { emptyStateViewHolder = EmptyStateViewHolder(it) }
    }

    fun withLoadStateHeader() {
        getRecyclerView()?.adapter = adapter.withLoadStateHeader(
            header = NetworkLoadStateAdapter()
        )
    }

    fun withLoadStateFooter() {
        getRecyclerView()?.adapter = adapter.withLoadStateFooter(
            footer = NetworkLoadStateAdapter()
        )
    }

    fun withLoadStateHeaderAndFooter() {
        getRecyclerView()?.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NetworkLoadStateAdapter(),
            footer = NetworkLoadStateAdapter()
        )
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