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
 * Last Updated at 8/4/20 8:13 PM.
 */

package com.binatestation.kickstart.repository.network.api

import com.binatestation.android.kickoff.utils.Constants
import com.binatestation.kickstart.repository.models.UOMModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UOMApi {

    @GET("https://devapi.mytyre.com/api/v1/admin/uoms")
    fun getAll(
        @Header("Authorization") token: String = "Bearer voyBHFPl--gG-Sr3SppYp5GI17DTvRE1_ijBKaYKOLE",
        @Query(Constants.GeneralConstants.KEY_PAGE) pageIndex: Int?,
        @Query(Constants.GeneralConstants.KEY_ITEMS) pageSize: Int?
    ): Call<List<UOMModel>>
}