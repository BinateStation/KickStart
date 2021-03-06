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
 * Last Updated at 5/1/20 2:48 PM.
 */

package com.binatestation.kickstart.ui.main.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.binatestation.kickstart.repository.UserRepository
import com.binatestation.kickstart.repository.network.RetrofitClientInstance
import com.binatestation.kickstart.repository.network.api.UserApi

class UserViewModel(application: Application) : AndroidViewModel(application) {


    private val userRepository =
        UserRepository(RetrofitClientInstance.getRetrofitInstance(getApplication()).create(UserApi::class.java))

    val users = userRepository.getAll()
}