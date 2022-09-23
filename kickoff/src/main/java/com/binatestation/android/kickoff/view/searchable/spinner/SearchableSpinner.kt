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
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.databinding.SearchableSpinnerDropdownItemBinding
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.repository.models.SearchableSpinnerDropdownItemModel
import com.binatestation.android.kickoff.repository.models.Selectable
import com.binatestation.android.kickoff.utils.adapters.RecyclerViewAdapter

/**
 * Spinner capable for search in dropdown for support paginated API.
 * Have an option to convert this to multiselect dropdown too.
 *
 * @author RKR
 */
class SearchableSpinner : ConstraintLayout {

    private var _searchHintString: String? = null
    private var selectedTextView: TextView? = null
    private var outlinedLabel: TextView? = null
    private var outlinedBg: View? = null
    private var doneButton: Button? = null
    private var popupLayoutView: View? = null
    private var popupWindow: PopupWindow? = null
    private var mDropdownAdapter: RecyclerViewAdapter = RecyclerViewAdapter()
    private var dropdownRecyclerView: RecyclerView? = null
    private var dropdownSearchView: SearchView? = null
    private var originalSelectableData: List<Selectable>? = null

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
            setOutlinedLabel()
        }

    private fun setOutlinedLabel() {
        if (outlined) {
            outlinedLabel?.visibility = hint?.let { outlinedLabel?.text = it; VISIBLE } ?: GONE
        }
    }

    var selectedItems: List<Selectable>? = null
        private set(value) {
            field = value
            selectedTextView?.text = value?.joinToString(", ") { it.getLabel().orEmpty() }
            onSelectMultipleItems?.let { it(value) }
            setOutlinedLabel()
        }

    var selectionList: List<Selectable>? = null
        set(value) {
            field = value
            value?.let { setData(it) }
        }

    var onQueryChange: SearchView.OnQueryTextListener? = null
        set(value) {
            field = value
            onQueryChange?.let { dropdownSearchView?.setOnQueryTextListener(it) }
        }

    var onSelectItem: ((Selectable?) -> Unit)? = null

    var onSelectMultipleItems: ((List<Selectable>?) -> Unit)? = null

    var multiSelectable: Boolean = false

    var showDoneButton: Boolean = false
        set(value) {
            field = value
            doneButton?.visibility = if (value) VISIBLE else GONE
        }
    var outlined: Boolean = false
        set(value) {
            field = value
            outlinedBg?.visibility = if (value) VISIBLE else GONE
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
        outlinedLabel = findViewById(R.id.outlined_label)
        outlinedBg = findViewById(R.id.outlined_bg_view)
        selectedTextView?.setOnClickListener { showDropDown() }
        popupLayoutView = LayoutInflater.from(this.context)
            .inflate(R.layout.searchable_spinner_drop_down, null, false)
        dropdownRecyclerView = popupLayoutView?.findViewById(R.id.recycler_view)
        dropdownSearchView = popupLayoutView?.findViewById(R.id.search_view)
        dropdownRecyclerView?.adapter = mDropdownAdapter
        selectedTextView?.post {
            popupWindow = PopupWindow(
                popupLayoutView,
                selectedTextView?.measuredWidth ?: WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                true
            )
            PopupWindowCompat.setOverlapAnchor(popupWindow!!, true)
            intDropdownAdapter()
            popupWindow?.setOnDismissListener {
                if (multiSelectable) {
                    selectedItems = getProcessedSelectedItems()
                }
            }
        }
    }

    private fun intDropdownAdapter() {
        mDropdownAdapter.showEmptyState = false
        mDropdownAdapter.itemViewTypeModels.add(
            ItemViewTypeModel(
                SearchableSpinnerDropdownItemModel::class.java,
                SearchableSpinnerViewHolder::class.java,
                SearchableSpinnerViewHolder.LAYOUT,
                SearchableSpinnerDropdownItemBinding::class.java
            )
        )
        mDropdownAdapter.setOnItemClickListener { `object`, _, _ ->
            if (`object` is Selectable) {
                if (!multiSelectable) {
                    selectedItem = originalSelectableData?.find { it.id == `object`.id }
                }
            }
            popupWindow?.dismiss()
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
        outlined = a.getBoolean(
            R.styleable.SearchableSpinner_outlined, false
        )

        a.recycle()
    }

    private fun showDropDown() {
        selectedTextView?.let {
            popupWindow?.showAsDropDown(it)
        }
    }

    fun setData(dataList: List<Selectable>) {
        originalSelectableData = dataList
        mDropdownAdapter.setTypedData(dataList.takeIf { it.isNotEmpty() }?.apply {
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
        }?.map { SearchableSpinnerDropdownItemModel(it, multiSelectable) })
    }

    fun resetSelection() {
        selectedItem = null
    }

    private fun getProcessedSelectedItems(): List<Selectable>? {
        return if (multiSelectable) {
            mDropdownAdapter.data?.filterIsInstance(Selectable::class.java)
                ?.filter { it.selected == true }
                ?.map { originalSelectableData?.find { original -> it.id == original.id }!! }
        } else selectedItem?.let { listOf(it) }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        intViews()
    }

}
