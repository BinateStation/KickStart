/*
 * Created By RKR
 * Last Updated at 14/12/19 5:23 PM.
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
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_image_switcher, parent, false)
        if (position < data.size) {
            with(getItem(position)) {
                //                if (this is BaseModel) {
//                    val image = view.findViewById<ImageView>(R.id.image_view)
//                    Utils.setProfileImage(image, name)
//                }
            }
        }

        return view
    }
}
