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
 * Last Updated at 8/4/20 8:43 PM.
 */

package com.binatestation.android.kickoff.utils.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.utils.setColorSchemeResources
import kotlinx.android.synthetic.main.fragment_swipe_list.*


/**
 * A simple [PagingDataListFragment] subclass. Which can be use for [SwipeRefreshLayout] with [PagingDataListFragment]
 */
open class PagingDataSwipeListFragment<DataModelType : Any>(comparator: DiffUtil.ItemCallback<DataModelType>) :
    PagingDataListFragment<DataModelType>(comparator) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_layout?.let { setColorSchemeResources(it) }
    }

    /**
     * function to set on refresh callback
     * @param onRefresh callback<Unit>
     */
    fun setOnRefreshListener(onRefresh: () -> Unit) {
        swipe_refresh_layout?.let { it.setOnRefreshListener { onRefresh() } }
    }

    /**
     * gets the [SwipeRefreshLayout] instance used in this [PagingDataSwipeListFragment]
     * @return SwipeRefreshLayout?
     */
    fun getSwipeRefreshLayout(): SwipeRefreshLayout? = swipe_refresh_layout

}