/*
 * Created By RKR
 * Last Updated at 14/12/19 5:40 PM.
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

package com.binatestation.android.kickoff.utils.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.utils.listeners.AdapterListener
import com.binatestation.android.kickoff.utils.listeners.ViewBinder


/**
 * Created by RKR on 08-12-2017.
 * BaseViewHolder
 */

abstract class BaseViewHolder constructor(
    itemView: View,
    val adapterListener: AdapterListener?
) :
    RecyclerView.ViewHolder(itemView), ViewBinder {

    init {
        adapterListener?.let {
            itemView.setOnClickListener { onClick(it) }
        }
    }

    fun onClick(view: View) {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION && adapterListener != null) {
            adapterListener.clickListener?.onClickItem(
                adapterListener.getItem(position),
                position,
                view
            )
        }
    }

    abstract override fun bindView(`object`: Any)
}
