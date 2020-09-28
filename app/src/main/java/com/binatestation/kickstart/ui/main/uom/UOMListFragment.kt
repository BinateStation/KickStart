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
 * Last Updated at 8/4/20 8:39 PM.
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
