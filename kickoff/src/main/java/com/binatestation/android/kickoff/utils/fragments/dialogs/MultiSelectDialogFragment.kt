/*
 * Created By RKR
 * Last Updated at 2/1/20 1:14 PM.
 *
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
 */

package com.binatestation.android.kickoff.utils.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.repository.models.MultiSelectDataModel
import com.binatestation.android.kickoff.repository.models.enums.Type
import com.binatestation.android.kickoff.utils.adapters.RecyclerViewAdapter
import com.binatestation.android.kickoff.utils.listeners.OnListItemClickListener
import kotlinx.android.synthetic.main.fragment_multi_select.*

/**
 * Created by RKR on 11-09-2018.
 * MultiSelectDialogFragment.
 */
class MultiSelectDialogFragment<T : MultiSelectDataModel> : BaseDialogFragment(),
    OnListItemClickListener {
    private var mRequestCode: Int = 0
    private var mMultiSelectable = true
    private var mSelectedItem: T? = null

    private var mAdapter: RecyclerViewAdapter? = null

    private lateinit var mListener: (ArrayList<T>) -> (Unit)

    /**
     * gets the adapter
     *
     * @return RecyclerViewAdapter
     */
    val adapter: RecyclerViewAdapter
        get() {
            if (mAdapter == null) {
                mAdapter = RecyclerViewAdapter()
                mAdapter?.clickListener = this
                mAdapter?.type = Type.SELECT.value
            }
            return mAdapter as RecyclerViewAdapter
        }

    @Suppress("UNCHECKED_CAST")
    private val selectedItems: ArrayList<T>
        get() {
            val selectedObjects = ArrayList<T>()
            if (mMultiSelectable) {
                addItems(adapter.data as ArrayList<T>, selectedObjects)
            } else {
                if (mSelectedItem is T) {
                    val selectedItem = mSelectedItem as T
                    selectedObjects.add(selectedItem)
                }
            }
            return selectedObjects
        }

    private fun addItems(data: ArrayList<T>, selectedObjects: ArrayList<T>) {
        for (`object` in data) {
            if (`object`.isSelected)
                selectedObjects.add(`object`)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRequestCode = arguments?.getInt(KEY_REQUEST_CODE) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recycler_view?.adapter = adapter
        action_positive.setOnClickListener { actionPositive() }
        action_negative.setOnClickListener { dismiss() }
    }

    @Suppress("unused")
    fun setMultiSelectable(multiSelectable: Boolean) {
        this.mMultiSelectable = multiSelectable
    }

    @Suppress("unused")
    fun setData(data: List<T>?) {
        adapter.setTypedData(data)
    }

    override fun onClickItem(`object`: Any, position: Int, actionView: View) {
        @Suppress("UNCHECKED_CAST")
        if (!mMultiSelectable) {
            adapter.data?.forEach { if (it is MultiSelectDataModel) it.isSelected = false }
            mSelectedItem = `object` as T
            `object`.isSelected = true
            actionPositive()
        }
    }

    private fun actionPositive() {
        mListener(selectedItems)
        dismiss()
    }

    companion object {
        const val TAG = "MultiSelectDialogFragme"

        private const val KEY_REQUEST_CODE = "REQUEST_CODE"

        fun <T : MultiSelectDataModel> newInstance(
            requestCode: Int,
            listener: (ArrayList<T>) -> (Unit)
        ): MultiSelectDialogFragment<T> {

            val args = Bundle()
            args.putInt(KEY_REQUEST_CODE, requestCode)
            val fragment = MultiSelectDialogFragment<T>()
            fragment.arguments = args
            fragment.isCancelable = false
            fragment.mListener = listener
            return fragment
        }
    }
}
