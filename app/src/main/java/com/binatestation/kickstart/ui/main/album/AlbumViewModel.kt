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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.binatestation.kickstart.repository.AlbumRepository
import com.binatestation.kickstart.repository.network.RetrofitClientInstance
import com.binatestation.kickstart.repository.network.api.AlbumApi

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumRepository =
        AlbumRepository(
            RetrofitClientInstance.getRetrofitInstance(getApplication()).create(
                AlbumApi::class.java
            )
        )

    val albums = albumRepository.getAll()
}