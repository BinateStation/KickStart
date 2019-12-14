/*
 * Created By RKR
 * Last Updated at 14/12/19 7:09 PM.
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

package com.binatestation.kickstart.repository.models

import com.binatestation.android.kickoff.utils.Constants.KEY_GRANT_TYPE
import com.binatestation.android.kickoff.utils.Constants.KEY_REFRESH_TOKEN
import com.google.gson.annotations.SerializedName

data class LoginModel(
    var username: String? = null,
    var password: String? = null
)

data class RefreshToken(
    @SerializedName(KEY_REFRESH_TOKEN)
    var refreshToken: String,
    @SerializedName(KEY_GRANT_TYPE)
    var grantType: String = KEY_REFRESH_TOKEN
)

data class Logout(
    var token: String
)