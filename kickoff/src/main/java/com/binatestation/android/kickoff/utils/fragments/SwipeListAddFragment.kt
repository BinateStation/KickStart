/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.binatestation.android.kickoff.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [SwipeListFragment] subclass. which can be used for [SwipeRefreshLayout]  with Add [FloatingActionButton]
 */
@Suppress("unused")
open class SwipeListAddFragment : SwipeListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe_list_add, container, false)
    }

    /**
     * Method to get on click callback for [FloatingActionButton] add
     * @param onClick callback<view View?, Unit>
     */
    fun setOnClickListener(onClick: (view: View?) -> Unit) {
        view?.findViewById<View>(R.id.action_add)?.let { it.setOnClickListener { v -> onClick(v) } }
    }
}
