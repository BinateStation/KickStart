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

package com.binatestation.kickstart.repository.network.api

import com.binatestation.kickstart.repository.models.PostModel
import com.binatestation.kickstart.utils.Constants.END_URL_POSTS
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {

    @POST(END_URL_POSTS)
    fun create(@Body postModel: PostModel)

    @GET(END_URL_POSTS)
    fun getAll(): Call<List<PostModel>>

    @GET("$END_URL_POSTS/{id}")
    fun get(@Path("id") id: Long): Call<PostModel>

}