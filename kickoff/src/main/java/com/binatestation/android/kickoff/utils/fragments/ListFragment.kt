/*
 * Created By RKR
 * Last Updated at 14/12/19 5:32 PM.
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

package com.binatestation.android.kickoff.utils.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.utils.adapters.RecyclerViewAdapter
import com.binatestation.android.kickoff.utils.listeners.OnListItemClickListener
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass.
 */
open class ListFragment : Fragment(), OnListItemClickListener {
    private var mAdapter: RecyclerViewAdapter? = null
    /**
     * gets the instance of LinearLayoutManager
     *
     * @return LinearLayoutManager
     */
    var linearLayoutManager: LinearLayoutManager? = null
        private set

    /**
     * gets the adapter
     *
     * @return RecyclerViewAdapter
     */
    open val adapter: RecyclerViewAdapter
        get() {
            if (mAdapter == null) {
                mAdapter = RecyclerViewAdapter()
                mAdapter?.clickListener = this
            }
            return mAdapter!!
        }

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
        context?.let {
            recycler_view?.layoutManager = LinearLayoutManager(context)
            recycler_view?.adapter = adapter
        }
    }

    override fun onClickItem(`object`: Any, position: Int, actionView: View) {

    }
}
