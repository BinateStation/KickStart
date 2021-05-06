/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.databinding.AdapterEmptyStateBinding
import com.binatestation.android.kickoff.repository.models.EmptyStateModel
import com.binatestation.android.kickoff.repository.models.NetworkState
import com.binatestation.android.kickoff.repository.models.enums.Status
import com.binatestation.android.kickoff.utils.adapters.PagedRecyclerViewAdapter
import com.binatestation.android.kickoff.utils.adapters.holders.EmptyStateViewHolder

/**
 * A simple [BaseListFragment] subclass. which can be used for recycler view with paging
 * Here we sets [PagedRecyclerViewAdapter] for the [RecyclerView] used in [BaseListFragment]
 */
@Deprecated(
    message = "PagedListFragment is deprecated and has been replaced by PagingDataListFragment",
    replaceWith = ReplaceWith(
        "PagingDataListFragment<DataModelType>",
        "com.binatestation.android.kickoff.utils.fragments.PagingDataListFragment"
    )
)
open class PagedListFragment<DataModelType : Any>(private val comparator: DiffUtil.ItemCallback<DataModelType>) :
    BaseListFragment() {
    private var mAdapter: PagedRecyclerViewAdapter<DataModelType>? = null

    private var networkState: NetworkState? = null

    private var emptyStateViewHolder: EmptyStateViewHolder? = null

    private var adapterEmptyStateBinding: AdapterEmptyStateBinding? = null

    /**
     * get [PagedRecyclerViewAdapter] object used in the [RecyclerView] of [BaseListFragment]
     */
    override val adapter: PagedRecyclerViewAdapter<DataModelType>
        get() {
            if (mAdapter == null) {
                mAdapter = PagedRecyclerViewAdapter(comparator = comparator)
            }
            return mAdapter!!
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterEmptyStateBinding = DataBindingUtil.bind(view.findViewById(R.id.empty_state))
        adapterEmptyStateBinding?.let { emptyStateViewHolder = EmptyStateViewHolder(it) }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        try {
            this.networkState = newNetworkState
            if (this.networkState == NetworkState.LOADED) {
                adapterEmptyStateBinding?.root?.visibility = View.GONE
            } else {
                adapterEmptyStateBinding?.root?.visibility = View.VISIBLE
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
