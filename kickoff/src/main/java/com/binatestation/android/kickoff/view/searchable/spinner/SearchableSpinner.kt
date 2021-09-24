/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.view.searchable.spinner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.PopupWindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.repository.models.Selectable

/**
 * Spinner capable for search in dropdown for support paginated API.
 * Have an option to convert this to multiselect dropdown too.
 *
 * @author RKR
 */
class SearchableSpinner : ConstraintLayout {

    private var _searchHintString: String? = null
    private var selectedTextView: TextView? = null
    private var doneButton: Button? = null
    private var popupLayoutView: View? = null
    private var popupWindow: PopupWindow? = null
    private var searchableSpinnerDropDownFragment: SearchableSpinnerDropDownFragment? = null

    /**
     * Selected hint
     */
    var hint: String?
        get() = selectedTextView?.hint?.toString()
        set(value) {
            selectedTextView?.hint = value
        }

    /**
     * Hint search view
     */
    var searchHint: String?
        get() = _searchHintString
        set(value) {
            _searchHintString = value
        }

    var selectedItem: Selectable? = null
        private set(value) {
            field = value
            selectedTextView?.text = value?.getLabel()
            onSelectItem?.let { it(value) }
        }

    var selectedItems: List<Selectable>? = null
        private set(value) {
            field = value
            selectedTextView?.text = value?.joinToString(", ") { it.getLabel().orEmpty() }
            onSelectMultipleItems?.let { it(value) }
        }

    var selectionList: List<Selectable>? = null
        set(value) {
            field = value
            value?.let { setData(it) }
        }

    var onQueryChange: SearchView.OnQueryTextListener? = null
        set(value) {
            field = value
            onQueryChange?.let { searchableSpinnerDropDownFragment?.setOnQueryTextListener(it) }
        }

    var onSelectItem: ((Selectable?) -> Unit)? = null

    var onSelectMultipleItems: ((List<Selectable>?) -> Unit)? = null

    var multiSelectable: Boolean = false
        set(value) {
            field = value
            searchableSpinnerDropDownFragment?.setMultiSelectable(true)
        }

    var showDoneButton: Boolean = false
        set(value) {
            field = value
            doneButton?.visibility = if (value) VISIBLE else GONE
        }

    constructor(context: Context) : super(context) {
        render(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        render(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        render(attrs, defStyle)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_searchable_spinner, this, true)
        intViews()
    }

    private fun intViews() {
        selectedTextView = findViewById(R.id.selected_text_view)
        doneButton = findViewById(R.id.action_done)
        selectedTextView?.setOnClickListener { showDropDown() }
        popupLayoutView = LayoutInflater.from(this.context)
            .inflate(R.layout.searchable_spinner_drop_down, null, false)
        selectedTextView?.post {
            popupWindow = PopupWindow(
                popupLayoutView,
                selectedTextView?.measuredWidth ?: WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                true
            )
            PopupWindowCompat.setOverlapAnchor(popupWindow!!, true)
            findDropdownFragment()
            popupWindow?.setOnDismissListener {
                if (multiSelectable) {
                    selectedItems = searchableSpinnerDropDownFragment?.selectedItems
                }
            }
        }
    }

    private fun findDropdownFragment() {
        var fragment: Fragment? = null
        if (context is FragmentActivity) {
            fragment =
                (context as FragmentActivity).supportFragmentManager.findFragmentById(R.id.searchable_spinner_dropdown_fragment)
        }
        if (fragment is SearchableSpinnerDropDownFragment) {
            searchableSpinnerDropDownFragment = fragment
            searchableSpinnerDropDownFragment?.setMultiSelectable(multiSelectable)
            fragment.setItemSelectListener {
                selectedItem = it
                popupWindow?.dismiss()
            }
        }
    }

    private fun render(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.SearchableSpinner, defStyle, 0
        )

        hint = a.getString(
            R.styleable.SearchableSpinner_hint
        )
        _searchHintString = a.getString(
            R.styleable.SearchableSpinner_searchHint
        )
        multiSelectable = a.getBoolean(
            R.styleable.SearchableSpinner_multiSelectable, false
        )

        showDoneButton = a.getBoolean(
            R.styleable.SearchableSpinner_showDoneButton, multiSelectable
        )

        a.recycle()
    }

    private fun showDropDown() {
        selectedTextView?.let {
            popupWindow?.showAsDropDown(it)
        }
    }

    fun setData(dataList: List<Selectable>) {
        searchableSpinnerDropDownFragment?.setData(dataList)
    }

    fun resetSelection() {
        selectedItem = null
    }

}
