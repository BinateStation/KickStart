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
 * Last Updated at 8/4/20 7:52 PM.
 */

package com.binatestation.android.kickoff.utils.fragments

import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.utils.adapters.RecyclerViewAdapter

/**
 * A simple [BaseListFragment] subclass. which specifies the [RecyclerViewAdapter] for the [RecyclerView] used in [BaseListFragment]
 */
open class ListFragment : BaseListFragment() {

    private var mAdapter: RecyclerViewAdapter? = null

    /**
     * get [RecyclerViewAdapter] object used in the [RecyclerView] of [BaseListFragment]
     */
    override val adapter: RecyclerViewAdapter
        get() {
            if (mAdapter == null) {
                mAdapter = RecyclerViewAdapter()
            }
            return mAdapter!!
        }

}