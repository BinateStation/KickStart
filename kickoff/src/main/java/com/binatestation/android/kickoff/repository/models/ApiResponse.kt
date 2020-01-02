/*
 * Created By RKR
 * Last Updated at 2/1/20 1:12 PM.
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

package com.binatestation.android.kickoff.repository.models

import android.util.Log
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.regex.Pattern

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiResponse<T> {
            if (error is ConnectException || error is UnknownHostException) {
                return ApiNoNetworkResponse(
                    "Please connect to the internet and try again."
                )
            }
            return ApiErrorResponse(
                error.message ?: "unknown error"
            )
        }

        fun <T> create(call: Call<T>, response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body,
                        linkHeader = response.headers()["link"],
                        requestId = call.request().header("request_id")
                    )
                }
            } else {

                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    try {
                        JSONObject(msg).optString("error")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        msg
                    }
                }
                ApiErrorResponse(
                    errorMsg ?: "unknown error"
                )
            }
        }
    }
}

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiNoNetworkResponse<T>(val errorMessage: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T,
    val links: Map<String, String>,
    val requestId: String
) : ApiResponse<T>() {
    constructor(body: T, linkHeader: String?, requestId: String?) : this(
        body = body,
        links = linkHeader?.extractLinks() ?: emptyMap(),
        requestId = requestId ?: "0"
    )

    @Suppress("unused")
    val nextPage: Int? by lazy(LazyThreadSafetyMode.NONE) {
        links[NEXT_LINK]?.let { next ->
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else {
                try {
                    Integer.parseInt(matcher.group(1) ?: "")
                } catch (ex: Throwable) {
                    Log.e("APIResponse", "cannot parse next page from %s", ex)
                    null
                }
            }
        }
    }

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2) ?: ""] = matcher.group(1) ?: ""
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

