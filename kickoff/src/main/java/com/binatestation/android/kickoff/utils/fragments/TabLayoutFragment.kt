/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.utils.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

/**
 * Created by RKR on 30-08-2018.
 * ViewPagerFragment.
 */
abstract class TabLayoutFragment : Fragment() {

    private var mViewPagerAdapter: ViewPagerAdapter? = null
    var viewPager: ViewPager? = null
    var tabLayout: TabLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        intView()
    }

    private fun intView() {
        tabLayout?.setupWithViewPager(viewPager)
        mViewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPager?.adapter = mViewPagerAdapter
    }

    @Suppress("unused")
    fun addFragment(fragment: Fragment, title: String) {
        mViewPagerAdapter?.let { it.addFrag(fragment, title); it.notifyDataSetChanged() }
    }

    /**
     * Function to get count of the fragments
     * @return Int
     */
    fun getCount(): Int {
        return mViewPagerAdapter?.count ?: 0
    }
}
