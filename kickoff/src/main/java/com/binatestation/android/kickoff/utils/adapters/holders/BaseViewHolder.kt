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
 * Last Updated at 9/4/20 3:56 PM.
 */

package com.binatestation.android.kickoff.utils.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.binatestation.android.kickoff.utils.listeners.ViewBinder


/**
 * Created by RKR on 08-12-2017.
 * BaseViewHolder
 */

abstract class BaseViewHolder constructor(
    itemView: View
) : RecyclerView.ViewHolder(itemView), ViewBinder {

    private var onClickItem: ((position: Int, actionView: View) -> Unit)? = null

    fun setOnItemClickListener(onClickItem: (position: Int, actionView: View) -> Unit) {
        this.onClickItem = onClickItem
    }

    init {
        itemView.setOnClickListener { onClickItem?.let { onClick -> onClick(adapterPosition, it) } }
    }

    fun setOnClick(view: View) {
        onClickItem?.let { onClick -> onClick(adapterPosition, view) }
    }

    abstract override fun bindView(`object`: Any?)

}
