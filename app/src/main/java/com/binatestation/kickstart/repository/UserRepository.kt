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
 * Last Updated at 5/1/20 2:44 PM.
 */

package com.binatestation.kickstart.repository

import androidx.lifecycle.LiveData
import com.binatestation.android.kickoff.repository.models.Resource
import com.binatestation.kickstart.repository.models.ApiRequest
import com.binatestation.kickstart.repository.models.UserModel
import com.binatestation.kickstart.repository.network.api.UserApi

class UserRepository(private val userApi: UserApi) {

    fun getAll(): LiveData<Resource<List<UserModel>>> {
        return object : NetworkBoundResource<List<UserModel>, ApiRequest>() {
            override fun saveCallResult(item: List<UserModel>) {
            }

            override fun shouldFetch(data: List<UserModel>?): Boolean {
                return true
            }

            override fun createCall() = userApi.getAll()

        }.asLiveData()
    }
}