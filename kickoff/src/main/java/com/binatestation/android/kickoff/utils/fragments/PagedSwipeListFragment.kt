/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
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

/**
 * A simple [PagedListFragment] subclass. Which can be use for [SwipeRefreshLayout] with [PagedListFragment]
 */
@Deprecated(
    message = "PagedSwipeListFragment is deprecated and has been replaced by PagingDataSwipeListFragment",
    replaceWith = ReplaceWith(
        "PagingDataSwipeListFragment<DataModelType>",
        "com.binatestation.android.kickoff.utils.fragments.PagingDataSwipeListFragment"
    )
)
open class PagedSwipeListFragment<DataModelType : Any>(comparator: DiffUtil.ItemCallback<DataModelType>) :
    PagedListFragment<DataModelType>(comparator) {

    private var swipeRefreshLayout: SwipeRefreshLayout? = null

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
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout?.let { setColorSchemeResources(it) }
    }

    /**
     * function to set on refresh callback
     * @param onRefresh callback<Unit>
     */
    fun setOnRefreshListener(onRefresh: () -> Unit) {
        swipeRefreshLayout?.let { it.setOnRefreshListener { onRefresh() } }
    }

    /**
     * gets the [SwipeRefreshLayout] instance used in this [PagedSwipeListFragment]
     * @return SwipeRefreshLayout?
     */
    fun getSwipeRefreshLayout(): SwipeRefreshLayout? = swipeRefreshLayout

}
