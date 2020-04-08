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
 * Last Updated at 8/4/20 9:07 PM.
 */

package com.binatestation.android.kickoff.utils.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.utils.listeners.ItemClickListener
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass. which have boiler plate [RecyclerView] int code
 */
abstract class BaseListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view?.adapter = adapter
    }

    /**
     * You can change the default layout manger using this method. the default [RecyclerView.LayoutManager] is [LinearLayoutManager]
     * @param layoutManager LayoutManager, you can use [LinearLayoutManager], [GridLayoutManager], [StaggeredGridLayoutManager]
     */
    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recycler_view?.layoutManager = layoutManager
    }

    /**
     * gets the [RecyclerView.LayoutManager] used in the [RecyclerView] or null
     * @return RecyclerView.LayoutManager?
     */
    fun getLayoutManager(): RecyclerView.LayoutManager? {
        return recycler_view?.layoutManager
    }

    /**
     * gets the [RecyclerView] instance used in this [BaseListFragment]
     * @return RecyclerView?
     */
    fun getRecyclerView(): RecyclerView? = recycler_view

    /**
     * sets the [RecyclerView] item click callback function
     * @param onItemClick callBack<selectedItem Any?, position Int, actionView View, Unit>
     */
    fun setOnItemClickListener(onItemClick: (`object`: Any?, position: Int, actionView: View) -> Unit) {
        if (adapter is ItemClickListener) {
            val itemClickLister = adapter as ItemClickListener
            itemClickLister.setOnItemClickListener(onItemClick)
        }
    }

    /**
     * This will sets [DividerItemDecoration] for the [RecyclerView] used in [BaseListFragment],
     * when [LinearLayoutManager] is used as [RecyclerView.LayoutManager]
     */
    fun setDividerItemDecoration() {
        recycler_view?.let {
            val layoutManager = getLayoutManager()
            if (layoutManager is LinearLayoutManager) {
                it.addItemDecoration(DividerItemDecoration(it.context, layoutManager.orientation))
            }
        }
    }

    abstract val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
}
