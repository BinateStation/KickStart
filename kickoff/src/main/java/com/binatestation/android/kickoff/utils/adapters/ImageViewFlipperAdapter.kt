/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.utils.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.binatestation.android.kickoff.R
import java.util.*


/**
 * Created by RKR on 30-08-2017.
 * ImageViewFlipperAdapter
 */

class ImageViewFlipperAdapter : BaseAdapter() {

    private var data = ArrayList<Any>()

    fun setData(data: ArrayList<*>) {
        this.data = ArrayList(data)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_image_switcher, parent, false)
    }
}
