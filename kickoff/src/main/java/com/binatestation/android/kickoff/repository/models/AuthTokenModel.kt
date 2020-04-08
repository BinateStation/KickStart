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
 * Last Updated at 7/4/20 7:33 PM.
 */

package com.binatestation.android.kickoff.repository.models

import com.binatestation.android.kickoff.utils.Constants.GeneralConstants.KEY_ACCESS_TOKEN
import com.binatestation.android.kickoff.utils.Constants.GeneralConstants.KEY_CREATED_AT
import com.binatestation.android.kickoff.utils.Constants.GeneralConstants.KEY_EXPIRES_IN
import com.binatestation.android.kickoff.utils.Constants.GeneralConstants.KEY_REFRESH_TOKEN
import com.binatestation.android.kickoff.utils.Constants.GeneralConstants.KEY_SCOPE
import com.binatestation.android.kickoff.utils.Constants.GeneralConstants.KEY_TOKEN_TYPE
import com.google.gson.annotations.SerializedName

data class AuthTokenModel(
    @SerializedName(KEY_ACCESS_TOKEN)
    var accessToken: String,
    @SerializedName(KEY_TOKEN_TYPE)
    var tokenType: String,
    @SerializedName(KEY_EXPIRES_IN)
    var expiresIn: Int,
    @SerializedName(KEY_REFRESH_TOKEN)
    var refreshToken: String,
    @SerializedName(KEY_SCOPE)
    var scope: String,
    @SerializedName(KEY_CREATED_AT)
    var createdAt: Long
)