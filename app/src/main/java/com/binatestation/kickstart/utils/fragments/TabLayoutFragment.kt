/*
 * Created By RKR
 * Last Updated at 14/12/19 4:09 PM.
 *
 * Copyright (c) 2019. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.binatestation.kickstart.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.binatestation.kickstart.R
import com.binatestation.kickstart.utils.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_tab_layout.*

/**
 * Created by RKR on 30-08-2018.
 * ViewPagerFragment.
 */
abstract class TabLayoutFragment : Fragment() {

    private var mViewPagerAdapter: ViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intView()
    }

    private fun intView() {
        tab_layout?.setupWithViewPager(view_pager)
        mViewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        view_pager?.adapter = mViewPagerAdapter
    }

    @Suppress("unused")
    fun addFragment(fragment: Fragment, title: String) {
        mViewPagerAdapter?.let { it.addFrag(fragment, title); it.notifyDataSetChanged() }
    }
}
