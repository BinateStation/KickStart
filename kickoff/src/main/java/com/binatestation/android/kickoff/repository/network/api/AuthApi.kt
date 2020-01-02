/*
 * Created By RKR
 * Last Updated at 2/1/20 1:26 PM.
 *
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
 */

package com.binatestation.android.kickoff.repository.network.api

import com.binatestation.android.kickoff.repository.models.AuthTokenModel
import com.binatestation.android.kickoff.repository.models.LoginModel
import com.binatestation.android.kickoff.repository.models.LogoutModel
import com.binatestation.android.kickoff.repository.models.RefreshTokenModel
import com.binatestation.android.kickoff.utils.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST(Constants.END_URL_OAUTH_TOKEN)
    fun login(@Body loginModel: LoginModel): Call<AuthTokenModel>

    @POST(Constants.END_URL_OAUTH_TOKEN)
    fun refreshToken(@Body refreshToken: RefreshTokenModel): Call<AuthTokenModel>

    @POST(Constants.END_URL_OAUTH_REVOKE)
    fun logout(@Body logout: LogoutModel): Call<Any>
}