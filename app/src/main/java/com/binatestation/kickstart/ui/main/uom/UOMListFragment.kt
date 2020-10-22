/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.kickstart.ui.main.uom

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.binatestation.android.kickoff.repository.models.ApiErrorThrowable
import com.binatestation.android.kickoff.repository.models.ApiNoDataThrowable
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.repository.models.NetworkState
import com.binatestation.android.kickoff.utils.fragments.PagingSwipeListAddSearchFragment
import com.binatestation.kickstart.databinding.AdapterUomBinding
import com.binatestation.kickstart.repository.models.UOMModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

class UOMListFragment : PagingSwipeListAddSearchFragment<UOMModel>(
    UOMModel.COMPARATOR
) {

    private lateinit var viewModel: UOMListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UOMListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        withLoadStateFooter()
        setOnRefreshListener { adapter.refresh() }
    }

    private fun initAdapter() {
        adapter.itemViewTypeModels.add(
            ItemViewTypeModel(
                UOMModel::class.java,
                UOMViewHolder::class.java,
                UOMViewHolder.LAYOUT,
                AdapterUomBinding::class.java
            )
        )

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                getSwipeRefreshLayout()?.isRefreshing = loadStates.refresh is LoadState.Loading
                when (loadStates.refresh) {
                    is LoadState.Loading -> {
                        setNetworkState(NetworkState.LOADING)
                    }
                    is LoadState.Error -> {
                        val errorState = loadStates.refresh as LoadState.Error
                        when (errorState.error) {
                            is ApiErrorThrowable -> {
                                setNetworkState(NetworkState.error(errorState.error.message))
                            }
                            is ApiNoDataThrowable -> {
                                setNetworkState(NetworkState.NO_DATA)
                            }
                            else -> {
                                setNetworkState(NetworkState.error("Unknown error"))
                            }
                        }
                    }
                    else -> {
                        setNetworkState(NetworkState.LOADED)
                    }
                }

            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.uoms.collectLatest {
                adapter.submitData(it)
            }
        }

    }
}
