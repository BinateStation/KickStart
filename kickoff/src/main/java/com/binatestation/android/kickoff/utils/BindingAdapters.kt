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

package com.binatestation.android.kickoff.utils

import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.utils.adapters.RecyclerViewAdapter
import com.binatestation.android.kickoff.utils.listeners.AdapterListener
import com.binatestation.android.kickoff.utils.listeners.OnListItemClickListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:src")
fun setImageButtonResource(view: View, resource: Int) {
    if (view is ImageButton)
        view.setImageResource(resource)
    else if (view is ImageView)
        view.setImageResource(resource)
}


@BindingAdapter("android:background")
fun setBackgroundResource(imageButton: AppCompatImageButton, resource: Int) {
    imageButton.setBackgroundResource(resource)
}


@BindingAdapter(value = ["addChips", "itemClick", "closeIconVisible"], requireAll = false)
fun addChips(
    chipGroup: ChipGroup,
    models: List<Any>?,
    clickListener: OnListItemClickListener?,
    closeIconVisible: Boolean = false
) {
    chipGroup.removeAllViews()
    models?.forEach { model -> actionAddChip(chipGroup, model, clickListener, closeIconVisible) }
}

private fun actionAddChip(
    chipGroup: ChipGroup,
    model: Any,
    clickListener: OnListItemClickListener?,
    closeIconVisible: Boolean
) {
    val chip = Chip(chipGroup.context)
    chip.text = model.toString()
    chip.isCloseIconVisible = closeIconVisible
    chip.tag = model
    chip.setOnClickListener { clickListener?.onClickItem(model, 1, chip) }
    chip.setOnCloseIconClickListener { clickListener?.onClickItem(model, 2, chip) }
    chipGroup.addView(chip)
}

@BindingAdapter("error")
fun setError(textInputLayout: TextInputLayout, error: String?) {
    if (TextUtils.isEmpty(error)) {
        textInputLayout.isErrorEnabled = false
    } else {
        textInputLayout.error = error
        textInputLayout.editText?.requestFocus()
    }
}

@BindingAdapter(value = ["adapter", "emptyState", "adapterListener"], requireAll = false)
fun setAdapter(
    recyclerView: RecyclerView,
    data: List<Any>?,
    emptyStateData: List<Any>?,
    adapterListener: AdapterListener?
) {
    var adapter = recyclerView.adapter
    if (adapter == null) {
        adapter = RecyclerViewAdapter()
        adapter.showEmptyState = false
        adapter.clickListener = adapterListener?.clickListener
    }
    if (adapter is RecyclerViewAdapter) {
        adapter.setTypedData(data)
        if (emptyStateData != null && data.isNullOrEmpty()) {
            adapter.setTypedData(emptyStateData)
        }
        recyclerView.adapter = adapter
    }
}

@BindingAdapter("showProgress")
fun showProgress(
    contentLoadingProgressBar: ContentLoadingProgressBar,
    showProgress: Boolean = false
) {
    if (showProgress) {
        contentLoadingProgressBar.show()
    } else {
        contentLoadingProgressBar.hide()
    }
}