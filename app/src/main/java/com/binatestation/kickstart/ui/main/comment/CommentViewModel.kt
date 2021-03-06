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
 * Last Updated at 5/1/20 11:28 AM.
 */

package com.binatestation.kickstart.ui.main.comment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.binatestation.kickstart.repository.CommentRepository
import com.binatestation.kickstart.repository.network.RetrofitClientInstance
import com.binatestation.kickstart.repository.network.api.CommentApi

class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val commentRepository =
        CommentRepository(
            RetrofitClientInstance.getRetrofitInstance(getApplication()).create(
                CommentApi::class.java
            )
        )

    val comments = commentRepository.getAll()
}