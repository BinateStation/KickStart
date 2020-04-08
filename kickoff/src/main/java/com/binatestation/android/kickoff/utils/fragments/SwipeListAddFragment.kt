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
 * Last Updated at 8/4/20 8:10 PM.
 */

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.binatestation.android.kickoff.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_swipe_list_add.*

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
        action_add?.let { it.setOnClickListener { v -> onClick(v) } }
    }
}
