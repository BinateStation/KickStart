/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.view.searchable.spinner

import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.databinding.SearchableSpinnerDropdownItemBinding
import com.binatestation.android.kickoff.repository.models.SearchableSpinnerDropdownItemModel
import com.binatestation.android.kickoff.utils.adapters.holders.BaseViewHolder

class SearchableSpinnerViewHolder(
    private val searchableSpinnerDropdownItemBinding: SearchableSpinnerDropdownItemBinding
) : BaseViewHolder(searchableSpinnerDropdownItemBinding.root) {

    override fun bindView(`object`: Any?) {
        if (`object` is SearchableSpinnerDropdownItemModel) {
            searchableSpinnerDropdownItemBinding.model = `object`
            searchableSpinnerDropdownItemBinding.executePendingBindings()
        }
    }

    companion object {
        var LAYOUT = R.layout.searchable_spinner_dropdown_item
    }
}
