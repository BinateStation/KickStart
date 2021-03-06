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

package com.binatestation.kickstart.ui.main.uom

import com.binatestation.android.kickoff.utils.adapters.holders.BaseViewHolder
import com.binatestation.kickstart.R
import com.binatestation.kickstart.databinding.AdapterUomBinding
import com.binatestation.kickstart.repository.models.UOMModel

class UOMViewHolder(
    private val adapterUomBinding: AdapterUomBinding
) : BaseViewHolder(adapterUomBinding.root) {
    companion object {
        var LAYOUT = R.layout.adapter_uom
    }

    override fun bindView(`object`: Any?) {
        if (`object` is UOMModel) {
            adapterUomBinding.model = `object`
            adapterUomBinding.executePendingBindings()
        }
    }

}