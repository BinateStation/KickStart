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
 * Last Updated at 30/5/20 11:08 PM.
 */

package com.binatestation.android.kickoff.repository.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.binatestation.android.kickoff.utils.Constants
import com.binatestation.android.kickoff.utils.Constants.GeneralConstants.KEY_LINK
import com.binatestation.android.kickoff.utils.Constants.GeneralConstants.KEY_REQUEST_ID
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
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
        fun <DataType> getCallback(
            data: MutableLiveData<ApiResponse<DataType>>? = null,
            errorObjectKey: String? = null,
            callBack: ((ApiResponse<DataType>) -> Unit)? = null
        ): Callback<DataType> {
            return object : Callback<DataType> {
                override fun onFailure(call: Call<DataType>, t: Throwable) {
                    val res = create<DataType>(t)
                    callBack?.let { it(res) }
                    data?.postValue(res)
                }

                override fun onResponse(
                    call: Call<DataType>,
                    response: Response<DataType>
                ) {
                    val res = create(call, response, errorObjectKey)
                    callBack?.let { it(res) }
                    data?.postValue(res)
                }
            }
        }

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

        fun <T> create(
            call: Call<T>? = null,
            response: Response<T>,
            errorObjectKey: String? = null
        ): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body,
                        requestId = call?.request()?.header(KEY_REQUEST_ID),
                        linkHeader = response.headers()[KEY_LINK],
                        currentPage = response.headers()[Constants.GeneralConstants.KEY_CURRENT_PAGE],
                        totalPages = response.headers()[Constants.GeneralConstants.KEY_TOTAL_PAGES],
                        pageItems = response.headers()[Constants.GeneralConstants.KEY_PAGE_ITEMS],
                        totalCount = response.headers()[Constants.GeneralConstants.KEY_TOTAL_COUNT]
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    try {
                        var jsonObject = JSONObject(msg)
                        errorObjectKey?.let {
                            jsonObject = jsonObject.optJSONObject(errorObjectKey) ?: JSONObject()
                        }
                        val keys = jsonObject.keys()
                        var message = ""
                        while (keys.hasNext()) {
                            val key = keys.next()
                            val listType =
                                object : TypeToken<List<String>>() {}.type
                            val errorList = Gson().fromJson<List<String>>(
                                jsonObject.optJSONArray(key)?.toString(),
                                listType
                            )
                            message = message.plus("$key ${errorList.joinToString()}")
                        }
                        message
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
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

/**
 * separate class for Network Error
 */
class ApiNoNetworkResponse<T>(val errorMessage: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T,
    val requestId: String,
    val links: Map<String, String>,
    val currentPage: String,
    val totalPages: String,
    val pageItems: String,
    val totalCount: String

) : ApiResponse<T>() {
    constructor(
        body: T,
        requestId: String?,
        linkHeader: String?,
        currentPage: String?,
        totalPages: String?,
        pageItems: String?,
        totalCount: String?
    ) : this(
        body = body,
        requestId = requestId ?: "0",
        links = linkHeader?.extractLinks() ?: emptyMap(),
        currentPage = currentPage ?: "0",
        totalPages = totalPages ?: "0",
        pageItems = pageItems ?: "0",
        totalCount = totalCount ?: "0"
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

