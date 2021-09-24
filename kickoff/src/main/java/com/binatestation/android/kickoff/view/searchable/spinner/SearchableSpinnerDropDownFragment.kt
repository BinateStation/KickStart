/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.view.searchable.spinner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.binatestation.android.kickoff.databinding.SearchableSpinnerDropdownItemBinding
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.repository.models.SearchableSpinnerDropdownItemModel
import com.binatestation.android.kickoff.repository.models.Selectable
import com.binatestation.android.kickoff.utils.fragments.SwipeListSearchFragment
import kotlinx.coroutines.FlowPreview


/**
 * A simple [Fragment] subclass.
 * Use to show dropdown for [SearchableSpinner]
 * create an instance of this fragment.
 */
class SearchableSpinnerDropDownFragment : SwipeListSearchFragment() {

    private var mMultiSelectable = false
    private var mSelectedItem: Selectable? = null
    private var itemSelectListener: ((Selectable) -> (Unit))? = null
    private var originalData: List<Selectable>? = null

    val selectedItems: List<Selectable>?
        get() {
            return if (mMultiSelectable) {
                adapter.data?.filterIsInstance(Selectable::class.java)
                    ?.filter { it.selected == true }
                    ?.map { originalData?.find { original -> it.id == original.id }!! }
            } else mSelectedItem?.let { listOf(it) }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListUi()
        setOnRefreshListener { getSwipeRefreshLayout()?.isRefreshing = false }
        setItemClickListener()
    }

    private fun setItemClickListener() {
        setOnItemClickListener { `object`, _, _ ->
            if (`object` is Selectable) {
                if (!mMultiSelectable) {
                    mSelectedItem = originalData?.find { it.id == `object`.id }
                    itemSelectListener?.let { mSelectedItem?.let { selectedItem -> it(selectedItem) } }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setUpListUi() {
        adapter.itemViewTypeModels.add(
            ItemViewTypeModel(
                SearchableSpinnerDropdownItemModel::class.java,
                SearchableSpinnerViewHolder::class.java,
                SearchableSpinnerViewHolder.LAYOUT,
                SearchableSpinnerDropdownItemBinding::class.java
            )
        )
    }

    fun setData(data: List<Selectable>?) {
        originalData = data
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
        }?.map { SearchableSpinnerDropdownItemModel(it, mMultiSelectable) })
    }

    fun setMultiSelectable(multiSelectable: Boolean) {
        this.mMultiSelectable = multiSelectable
    }

    fun setItemSelectListener(listener: (Selectable) -> (Unit)) {
        itemSelectListener = listener
    }

}
