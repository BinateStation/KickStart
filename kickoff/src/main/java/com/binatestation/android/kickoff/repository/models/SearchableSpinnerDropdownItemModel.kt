/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.repository.models

data class SearchableSpinnerDropdownItemModel(
    override val id: Long?,
    override var selected: Boolean?,
    val labelString: String?,
    val multiSelect: Boolean = false
) : Selectable {

    constructor(selectable: Selectable, isMultiSelect: Boolean) : this(
        selectable.id,
        selectable.selected,
        selectable.getLabel(),
        multiSelect = isMultiSelect
    )

    override fun getLabel(): String? {
        return labelString
    }

}
