/*
 * (c) Binate Station Private Limited. All rights reserved.
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

/**
 * A simple [Fragment] subclass. which have boiler plate [RecyclerView] int code
 */
abstract class BaseListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

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
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
    }

    /**
     * You can change the default layout manger using this method. the default [RecyclerView.LayoutManager] is [LinearLayoutManager]
     * @param layoutManager LayoutManager, you can use [LinearLayoutManager], [GridLayoutManager], [StaggeredGridLayoutManager]
     */
    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
    }

    /**
     * gets the [RecyclerView.LayoutManager] used in the [RecyclerView] or null
     * @return RecyclerView.LayoutManager?
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun getLayoutManager(): RecyclerView.LayoutManager? {
        return recyclerView.layoutManager
    }

    /**
     * gets the [RecyclerView] instance used in this [BaseListFragment]
     * @return RecyclerView?
     */
    fun getRecyclerView(): RecyclerView = recyclerView

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
        val layoutManager = getLayoutManager()
        if (layoutManager is LinearLayoutManager) {
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    layoutManager.orientation
                )
            )
        }
    }

    abstract val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
}
