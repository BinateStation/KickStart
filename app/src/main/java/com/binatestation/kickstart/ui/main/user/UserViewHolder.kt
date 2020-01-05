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
 * Last Updated at 5/1/20 2:47 PM.
 */

package com.binatestation.kickstart.ui.main.user

import com.binatestation.android.kickoff.utils.adapters.holders.BaseViewHolder
import com.binatestation.android.kickoff.utils.listeners.AdapterListener
import com.binatestation.kickstart.R
import com.binatestation.kickstart.databinding.AdapterUserBinding
import com.binatestation.kickstart.repository.models.UserModel

class UserViewHolder(
    adapterListener: AdapterListener,
    private val adapterUserBinding: AdapterUserBinding
) : BaseViewHolder(adapterUserBinding.root, adapterListener) {

    override fun bindView(`object`: Any) {
        if (`object` is UserModel) {
            adapterUserBinding.model = `object`
            adapterUserBinding.executePendingBindings()
        }
    }

    companion object {
        var LAYOUT = R.layout.adapter_user
    }

}
