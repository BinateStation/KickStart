/*
 * (c) Binate Station Private Limited. All rights reserved.
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
        itemView.setOnClickListener { onClickItem?.let { onClick -> onClick(bindingAdapterPosition, it) } }
    }

    @Suppress("unused")
    fun setOnClick(view: View) {
        onClickItem?.let { onClick -> onClick(bindingAdapterPosition, view) }
    }

    abstract override fun bindView(`object`: Any?)

}
