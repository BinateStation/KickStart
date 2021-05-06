/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.binatestation.android.kickoff.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [PagedSwipeListFragment] subclass. which can be used for swipe refresh layout with Add [FloatingActionButton]
 */
@Suppress("unused")
@Deprecated(
    message = "PagedSwipeListAddFragment is deprecated and has been replaced by PagingSwipeListAddFragment",
    replaceWith = ReplaceWith(
        "PagingSwipeListAddFragment<DataModelType>",
        "com.binatestation.android.kickoff.utils.fragments.PagingSwipeListAddFragment"
    )
)
open class PagedSwipeListAddFragment<DataModelType : Any>(comparator: DiffUtil.ItemCallback<DataModelType>) :
    PagedSwipeListFragment<DataModelType>(comparator) {

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
