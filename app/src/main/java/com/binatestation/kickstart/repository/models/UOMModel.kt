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
 * Last Updated at 8/4/20 6:25 PM.
 */

package com.binatestation.kickstart.repository.models


import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class UOMModel(
    var id: Long? = null,
    var name: String? = null,
    @SerializedName("uom_category_id")
    var uomCategoryId: Long? = null,
    var active: Boolean? = null,
    var factor: String? = null,
    var rounding: String? = null,
    @SerializedName("uom_type")
    var uomType: String? = null,
    @SerializedName("measure_type")
    var measureType: String? = null
) {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<UOMModel>() {
            override fun areItemsTheSame(oldItem: UOMModel, newItem: UOMModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: UOMModel, newItem: UOMModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}