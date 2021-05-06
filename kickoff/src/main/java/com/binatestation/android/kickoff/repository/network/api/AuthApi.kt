/*
 * (c) Binate Station Private Limited. All rights reserved.
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

@Suppress("unused")
interface AuthApi {

    @POST(Constants.EndUrls.END_URL_OAUTH_TOKEN)
    fun login(@Body loginModel: LoginModel): Call<AuthTokenModel>

    @POST(Constants.EndUrls.END_URL_OAUTH_TOKEN)
    fun refreshToken(@Body refreshToken: RefreshTokenModel): Call<AuthTokenModel>

    @POST(Constants.EndUrls.END_URL_OAUTH_REVOKE)
    fun logout(@Body logout: LogoutModel): Call<Any>
}
