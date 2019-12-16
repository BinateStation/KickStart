/*
 * Created By RKR
 * Last Updated at 14/12/19 11:18 PM.
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

package com.binatestation.kickstart.ui.main.post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.binatestation.kickstart.repository.PostRepository
import com.binatestation.kickstart.repository.network.RetrofitClientInstance
import com.binatestation.kickstart.repository.network.api.PostApi

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postRepository =
        PostRepository(RetrofitClientInstance.getRetrofitInstance(getApplication()).create(PostApi::class.java))

    val posts = postRepository.getAll()
}