/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.utils.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.repository.models.Selectable
import com.binatestation.android.kickoff.utils.adapters.RecyclerViewAdapter

/**
 * Created by RKR on 11-09-2018.
 * MultiSelectDialogFragment.
 */
open class MultiSelectDialogFragment<T : Selectable> : BaseDialogFragment() {

    private var mRequestCode: Int = 0
    private var mMultiSelectable = true
    private var mSelectedItem: Selectable? = null

    private var mAdapter: RecyclerViewAdapter? = null

    private lateinit var mListener: (List<Selectable>?) -> (Unit)

    /**
     * gets the adapter
     *
     * @return RecyclerViewAdapter
     */
    val adapter: RecyclerViewAdapter
        get() {
            if (mAdapter == null) {
                mAdapter = RecyclerViewAdapter()
                mAdapter?.setOnItemClickListener { `object`: Any?, _: Int, _: View ->
                    onClickItem(`object`)
                }
            }
            return mAdapter as RecyclerViewAdapter
        }

    @Suppress("UNCHECKED_CAST")
    private val selectedItems: List<Selectable>?
        get() {
            return if (mMultiSelectable) {
                adapter.data?.filterIsInstance(Selectable::class.java)
                    ?.filter { it.selected == true }
            } else mSelectedItem?.let { listOf(it) }
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
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerView?.adapter = adapter
        view.findViewById<View>(R.id.action_positive)?.setOnClickListener { actionPositive() }
        view.findViewById<View>(R.id.action_negative)?.setOnClickListener { dismiss() }
    }

    @Suppress("unused")
    fun setMultiSelectable(multiSelectable: Boolean) {
        this.mMultiSelectable = multiSelectable
    }

    @Suppress("unused")
    fun setData(data: List<Selectable>?) {
        adapter.setTypedData(`data`?.takeIf { it.isNotEmpty() }?.apply {
            val mutableData = this as MutableList
            val selectedItems = selectedItems
            selectedItems?.forEach {
                if (mutableData.contains(it)) {
                    val index = mutableData.indexOf(it)
                    mutableData[index].selected = it.selected
                } else {
                    mutableData.add(0, it)
                }
            }
        })
    }

    private fun onClickItem(`object`: Any?) {
        if (!mMultiSelectable) {
            adapter.data?.filterIsInstance(Selectable::class.java)
                ?.find { it.selected == true }?.selected = false
            mSelectedItem = `object` as Selectable
            `object`.selected = true
            actionPositive()
        }
    }

    private fun actionPositive() {
        mListener(selectedItems)
        dismiss()
    }

    fun setListener(listener: (List<Selectable>?) -> (Unit)) {
        mListener = listener
    }

    companion object {

        private const val KEY_REQUEST_CODE = "REQUEST_CODE"

        fun <T : Selectable> newInstance(
            requestCode: Int,
            listener: (List<Selectable>?) -> (Unit)
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
