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
 * Last Updated at 8/4/20 8:27 PM.
 */

package com.binatestation.android.kickoff.utils.adapters.holders

import android.graphics.drawable.AnimatedVectorDrawable
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.databinding.AdapterEmptyStateBinding
import com.binatestation.android.kickoff.repository.models.EmptyStateModel


/**
 * Created by RKR on 21-09-2017.
 * EmptyStateViewHolder
 */

class EmptyStateViewHolder(
    private val adapterEmptyStateBinding: AdapterEmptyStateBinding
) : BaseViewHolder(adapterEmptyStateBinding.root) {

    override fun bindView(`object`: Any?) {
        if (`object` is EmptyStateModel) {
            adapterEmptyStateBinding.model = `object`
            adapterEmptyStateBinding.executePendingBindings()
            val drawable = adapterEmptyStateBinding.icon.drawable
            if (drawable is AnimatedVectorDrawableCompat) {
                drawable.start()
            } else if (drawable is AnimatedVectorDrawable) {
                drawable.start()
            }
        }
    }

    companion object {
        var LAYOUT = R.layout.adapter_empty_state
    }

}
