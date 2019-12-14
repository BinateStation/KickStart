/*
 * Created By RKR
 * Last Updated at 14/12/19 5:47 PM.
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

package com.binatestation.kickstart.repository.network

import android.content.Context
import com.binatestation.android.kickoff.utils.Constants.END_URL_OAUTH_TOKEN
import com.binatestation.kickstart.BuildConfig
import com.binatestation.kickstart.repository.models.AuthTokenModel
import com.binatestation.kickstart.utils.Session
import com.google.gson.Gson
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class TokenAuthenticator(val context: Context) : Authenticator {
    @Throws(IOException::class)

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = Session.getToken(context)
        return when {
            refreshToken(
                BuildConfig.BASE_URL,
                accessToken.refreshToken
            ) -> {
                val token = Session.getToken(context)
                // make current request with new access token
                response.request.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "${token.tokenType} ${token.accessToken}")
                    .build()

            }
            else -> // refresh failed , maybe you can logout user
                // returning null is critical here, because if you do not return null
                // it will try to refresh token continuously like 1000 times.
                // also you can try 2-3-4 times by depending you before logging out your user
                null
        }
    }

    @Throws(IOException::class)
    private fun refreshToken(url: String, refresh: String): Boolean {
        val refreshUrl = URL(url + END_URL_OAUTH_TOKEN)
        val urlConnection = refreshUrl.openConnection() as HttpURLConnection
        urlConnection.doInput = true
        urlConnection.requestMethod = "POST"
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        urlConnection.useCaches = false
        val urlParameters =
            "grant_type=refresh_token&refresh_token=$refresh"

        urlConnection.doOutput = true
        val wr = DataOutputStream(urlConnection.outputStream)
        wr.writeBytes(urlParameters)
        wr.flush()
        wr.close()

        val responseCode = urlConnection.responseCode

        if (responseCode == 200) {
            val `in` = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val response = StringBuilder()

            var data = `in`.read()
            while (data != -1) {
                val current = data.toChar()
                data = `in`.read()
                response.append(current)
            }
            `in`.close()

            val gson = Gson()
            val accessTokenModel = gson.fromJson(response.toString(), AuthTokenModel::class.java)
            Session.setToken(context, accessTokenModel)
            val expiresAt = Calendar.getInstance()
            expiresAt.add(Calendar.SECOND, accessTokenModel.expiresIn)
            Session.setTokenExpiresAt(context, expiresAt.timeInMillis)
            return true

        } else {
            return false
        }

    }


}
