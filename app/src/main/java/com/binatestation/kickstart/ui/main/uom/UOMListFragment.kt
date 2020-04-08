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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.repository.models.NetworkState
import com.binatestation.android.kickoff.utils.fragments.PagedSwipeListAddSearchFragment
import com.binatestation.kickstart.databinding.AdapterUomBinding
import com.binatestation.kickstart.repository.models.UOMModel

class UOMListFragment : PagedSwipeListAddSearchFragment<UOMModel>(
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
        setOnRefreshListener { viewModel.refresh() }
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
        viewModel.uoms.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it) {
                // Workaround for an issue where RecyclerView incorrectly uses the loading / spinner
                // item added to the end of the list as an anchor during initial load.
                val layoutManager = (getLayoutManager() as LinearLayoutManager)
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION) {
                    getRecyclerView()?.scrollToPosition(position)
                }
            }
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })
        viewModel.refreshState.observe(viewLifecycleOwner, Observer {
            getSwipeRefreshLayout()?.isRefreshing = it == NetworkState.LOADING
        })
    }
}
