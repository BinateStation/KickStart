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
 * Last Updated at 8/4/20 8:29 PM.
 */

package com.binatestation.kickstart.ui.main.uom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.binatestation.kickstart.repository.UOMRepository
import com.binatestation.kickstart.repository.local.KickStartDb
import com.binatestation.kickstart.repository.network.RetrofitClientInstance
import com.binatestation.kickstart.repository.network.api.UOMApi

class UOMListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UOMRepository(
        RetrofitClientInstance.getRetrofitInstance(getApplication()).create(UOMApi::class.java),
        KickStartDb.getInstance(getApplication()).uomDao()
    )
    val uoms = repository.getAll(5)

}
