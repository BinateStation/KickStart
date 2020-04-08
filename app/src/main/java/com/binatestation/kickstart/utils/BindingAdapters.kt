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
 * Last Updated at 7/4/20 8:21 PM.
 */

package com.binatestation.kickstart.utils

import android.graphics.Bitmap
import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

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


@BindingAdapter("error")
fun setError(textInputLayout: TextInputLayout, error: String?) {
    if (TextUtils.isEmpty(error)) {
        textInputLayout.isErrorEnabled = false
    } else {
        textInputLayout.error = error
        textInputLayout.editText?.requestFocus()
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
                com.binatestation.android.kickoff.utils.Utils.setCircleProfileImage(view, it)
            } else {
                com.binatestation.android.kickoff.utils.Utils.setImage(view, it)
            }
    }
    bitmap?.let {
        if (view is ImageView)
            if (circular == true) {
                com.binatestation.android.kickoff.utils.Utils.setCircleProfileImage(view, it)
            } else {
                com.binatestation.android.kickoff.utils.Utils.setImage(view, it)
            }
    }
}