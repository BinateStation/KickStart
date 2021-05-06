/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.utils

import android.graphics.Bitmap
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.utils.adapters.RecyclerViewAdapter

/**
 * @author RKR #rkrsmail@gmail.com
 * @since v1.0.2.4
 */

/**
 * Binding adapter used to change the visibility of given view according to the param
 *
 * @param view the view need to change the visibility
 * @param visible the flag to choose visibility
 */
@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * Binding adapter to set the image resource for both #ImageButton and #ImageView
 *
 * @param view view to set image resource
 * @param resource drawable resource id
 */
@BindingAdapter("android:src")
fun setImageButtonResource(view: View, resource: Int) {
    if (view is ImageButton)
        view.setImageResource(resource)
    else if (view is ImageView)
        view.setImageResource(resource)
}

/**
 * Binding adapter to set background resource to an image button
 *
 * @param imageButton view to set background resource
 * @param resource drawable resource id
 */
@BindingAdapter("android:background")
fun setBackgroundResource(imageButton: AppCompatImageButton, resource: Int) {
    imageButton.setBackgroundResource(resource)
}

/**
 * Binding adapter to switch content loading progress
 *
 * @param contentLoadingProgressBar
 * @param showProgress
 */
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

@Suppress("DEPRECATION")
@BindingAdapter("htmlText")
fun setText(textView: TextView, text: String?) {
    text?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            textView.text = Html.fromHtml(text)
        }
    }
}

@BindingAdapter("android:src", "path", "bitmap", "circular", requireAll = false)
fun setImageResource(
    view: View,
    resource: Int?,
    path: String?,
    bitmap: Bitmap?,
    circular: Boolean?
) {
    resource?.let {
        if (view is ImageButton)
            view.setImageResource(resource)
        if (view is ImageView)
            view.setImageResource(resource)
    }
    path?.let {
        if (view is ImageView)
            if (circular == true) {
                Utils.setCircleProfileImage(view, it)
            } else {
                Utils.setImage(view, it)
            }
    }
    bitmap?.let {
        if (view is ImageView)
            if (circular == true) {
                Utils.setCircleProfileImage(view, it)
            } else {
                Utils.setImage(view, it)
            }
    }
}

/**
 * Binding adapter used to highlight the search term in the list
 *
 * @param textView [TextView] which have search term
 * @param query String? The query term
 */
@BindingAdapter("query")
fun searchHighlight(textView: TextView, query: String?) {
    query?.let {
        val text = textView.text
        val wordToSpan: Spannable =
            SpannableString(textView.text)
        wordToSpan.setSpan(
            ForegroundColorSpan(textView.currentTextColor),
            0,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val start = text.findAnyOf(listOf(query), ignoreCase = true)
        start?.first?.let {
            wordToSpan.setSpan(
                ForegroundColorSpan(textView.highlightColor),
                it,
                it + start.second.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textView.text = wordToSpan
    }
}

@BindingAdapter("recyclerViewAdapter")
fun setRecyclerViewAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerViewAdapter? = null
) {
    recyclerView.adapter = adapter
}
