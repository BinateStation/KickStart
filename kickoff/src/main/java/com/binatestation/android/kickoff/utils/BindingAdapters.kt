/*
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
 *
 * Last Updated at 24/5/20 1:00 PM.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.utils

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter

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
@BindingAdapter("android:text")
fun setText(textView: TextView, text: String?) {
    text?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            textView.text = Html.fromHtml(text)
        }
    }
}