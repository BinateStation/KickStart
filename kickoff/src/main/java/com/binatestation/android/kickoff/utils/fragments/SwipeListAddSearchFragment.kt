/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.binatestation.android.kickoff.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [SwipeListAddFragment] subclass. which can be used for [SearchView], [SwipeRefreshLayout] with Add [FloatingActionButton]
 */
open class SwipeListAddSearchFragment : SwipeListAddFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe_list_add_search, container, false)
    }

    /**
     * method to sets on Query text change listener
     * @param onQueryTextListener OnQueryTextListener
     */
    fun setOnQueryTextListener(onQueryTextListener: SearchView.OnQueryTextListener) {
        view?.findViewById<SearchView>(R.id.search_view)
            ?.setOnQueryTextListener(onQueryTextListener)
    }
}
