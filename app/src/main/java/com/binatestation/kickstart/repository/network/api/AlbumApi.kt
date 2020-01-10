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
 * Last Updated at 5/1/20 12:27 PM.
 */

package com.binatestation.kickstart.repository.network.api

import com.binatestation.kickstart.repository.models.AlbumModel
import com.binatestation.kickstart.utils.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumApi {
    @POST(Constants.END_URL_ALBUMS)
    fun create(@Body albumModel: AlbumModel)

    @GET(Constants.END_URL_ALBUMS)
    fun getAll(): Call<List<AlbumModel>>

    @GET("${Constants.END_URL_ALBUMS}/{id}")
    fun get(@Path("id") id: Long): Call<AlbumModel>
}