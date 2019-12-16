/*
 * Created By RKR
 * Last Updated at 15/12/19 12:08 AM.
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

package com.binatestation.kickstart.ui.main.post

import com.binatestation.android.kickoff.utils.adapters.holders.BaseViewHolder
import com.binatestation.android.kickoff.utils.listeners.AdapterListener
import com.binatestation.kickstart.R
import com.binatestation.kickstart.databinding.AdapterPostBinding
import com.binatestation.kickstart.repository.models.PostModel

class PostViewHolder(
    adapterListener: AdapterListener,
    private val adapterPostBinding: AdapterPostBinding
) : BaseViewHolder(adapterPostBinding.root, adapterListener) {

    override fun bindView(`object`: Any) {
        if (`object` is PostModel) {
            adapterPostBinding.model = `object`
            adapterPostBinding.executePendingBindings()
        }
    }

    companion object {
        var LAYOUT = R.layout.adapter_post
    }

}
