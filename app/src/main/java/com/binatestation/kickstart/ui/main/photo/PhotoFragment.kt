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
 * Last Updated at 5/1/20 2:52 PM.
 */

package com.binatestation.kickstart.ui.main.photo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.utils.fragments.ListFragment
import com.binatestation.kickstart.databinding.AdapterPhotoBinding
import com.binatestation.kickstart.repository.models.PhotoModel

class PhotoFragment : ListFragment() {

    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoViewModel = ViewModelProvider(this)[PhotoViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.itemViewTypeModels.add(
            ItemViewTypeModel(
                PhotoModel::class.java,
                PhotoViewHolder::class.java,
                PhotoViewHolder.LAYOUT,
                AdapterPhotoBinding::class.java
            )
        )
        photoViewModel.photos.observe(viewLifecycleOwner, Observer { it ->
            it.data?.let {
                adapter.setTypedData(it)
            }
        })
    }
}