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
 * Last Updated at 8/4/20 9:07 PM.
 */

package com.binatestation.kickstart.ui.main.post

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.binatestation.android.kickoff.repository.models.ItemViewTypeModel
import com.binatestation.android.kickoff.utils.fragments.ListFragment
import com.binatestation.kickstart.databinding.AdapterPostBinding
import com.binatestation.kickstart.repository.models.PostModel

class PostFragment : ListFragment() {

    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDividerItemDecoration()
        adapter.itemViewTypeModels.add(
            ItemViewTypeModel(
                PostModel::class.java,
                PostViewHolder::class.java,
                PostViewHolder.LAYOUT,
                AdapterPostBinding::class.java
            )
        )
        postViewModel.posts.observe(viewLifecycleOwner, { it ->
            it.data?.let {
                adapter.setTypedData(it)
            }
        })
    }
}