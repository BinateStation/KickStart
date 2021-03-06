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
 * Last Updated at 5/1/20 12:38 PM.
 */

package com.binatestation.kickstart.ui.main.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.utils.fragments.ListFragment
import com.binatestation.kickstart.R
import com.binatestation.kickstart.databinding.AdapterAlbumBinding
import com.binatestation.kickstart.databinding.FragmentAlbumBinding
import com.binatestation.kickstart.repository.models.AlbumModel

class AlbumFragment : ListFragment() {

    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var fragmentAlbumBinding: FragmentAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumViewModel = ViewModelProvider(this)[AlbumViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentAlbumBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_album,
            container,
            false
        )
        fragmentAlbumBinding.lifecycleOwner = this
        fragmentAlbumBinding.imgRes = R.drawable.ic_camera_alt_black_24dp
        return fragmentAlbumBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.itemViewTypeModels.add(
            ItemViewTypeModel(
                AlbumModel::class.java,
                AlbumViewHolder::class.java,
                AlbumViewHolder.LAYOUT,
                AdapterAlbumBinding::class.java
            )
        )
        albumViewModel.albums.observe(viewLifecycleOwner, { it ->
            it.data?.let {
                adapter.setTypedData(it)
            }
        })
    }
}